import {Component, OnInit} from '@angular/core';
import {RestService} from '../rest/rest.service';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  timeout = null;
  text = 'Waiting';
  grid: string[][] = null;
  game: {
    tableHeight: number,
    tableWidth: number,
    playerNames: string[],
    tableState: {cells: {row: number, column: number, player: number}[]},
    gameStatus: string
  } = null;

  constructor(private restService: RestService, private cookieService: CookieService, private router: Router) {
  }

  ngOnInit() {
    if (!this.cookieService.check('gameCode')) {
      this.backToLobby();
    }
    this.getGame();
  }

  buildGrid() {
    this.grid = [];
    for (let i = 0; i < this.game.tableHeight; ++i) {
      const row = [];
      for (let j = 0; j < this.game.tableWidth; ++j) {
        row.push('_');
      }
      this.grid.push(row);
    }
    for (const cell of this.game.tableState.cells) {
      this.grid[cell.row - 1][cell.column - 1] = cell.player === 1 ? 'X' : 'O';
    }
  }

  getGame(triggerWait = true) {
    if (triggerWait) {
      this.text = 'Waiting';
    }
    this.restService.getActiveGame()
      .then((game: any) => {
        this.game = game;
        this.buildGrid();
        if (this.game.gameStatus !== 'NOT_FINISHED') {
          this.text = (this.game.gameStatus === 'LOSE' ? 'Lose' : 'Draw');
          this.restService.performMove(0, 0)
            .then(() => this.timeout = setTimeout(() => this.backToLobby(), 3500));
        } else if (triggerWait) {
          this.waitForOpponent();
        }
      })
      .catch(() => this.backToLobby());
  }

  backToLobby() {
    clearTimeout(this.timeout);
    this.router.navigate(['/lobby']);
  }

  myTurn() {
    this.text = 'Your turn';
  }

  waitForOpponent() {
    this.restService.hasOpponentMoved()
      .then(ans => {
        if (ans === 'true') {
          clearTimeout(this.timeout);
          this.myTurn();
          this.getGame(false);
        } else {
          this.timeout = setTimeout(() => this.waitForOpponent(), 1000);
        }
      })
      .catch(e => console.log(e));
  }

  movingPlayer() {
    let cnt = 0;
    for (const cell of this.game.tableState.cells) {
      cnt += cell.player === 1 ? 1 : -1;
    }
    return cnt === 1 ? 'O' : 'X';
  }

  performMove(row: number, column: number) {
    this.restService.performMove(row, column)
      .then(status => {
        if (status === 'NOT_FINISHED') {
          this.getGame();
        } else {
          this.grid[row - 1][column - 1] = this.movingPlayer();
          this.text = (status === 'DRAW' ? 'Draw' : 'Win');
          this.timeout = setTimeout(() => this.backToLobby(), 3500);
        }
      })
      .catch(e => console.log(e));
  }
}
