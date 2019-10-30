import {Component, OnInit} from '@angular/core';
import {RestService} from '../rest/rest.service';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit {
  timeout = null;
  newGame = {
    hostId: this.cookieService.get('userId'),
    tableHeight: 3,
    tableWidth: 3,
    lineSize: 3,
    gamePublic: true
  };
  joinCode = '';
  games: {
    code: string,
    hostName: string,
    tableHeight: number,
    tableWidth: number,
    lineSize: number
  }[] = [];
  lobbyError = false;

  constructor(private cookieService: CookieService, private restService: RestService, private router: Router) {
  }

  goToLobby() {
    if (this.timeout) {
      clearTimeout(this.timeout);
    }
    this.router.navigate(['/lobby']);
  }

  createGame() {
    this.restService.createGame(this.newGame)
      .then(() => this.goToLobby());
  }

  getGames() {
    this.restService.getPublicGames()
      .then((games: any) => {
        this.games = games;
        this.timeout = setTimeout(() => this.getGames(), 2000);
      })
      .catch(() => {
        this.cookieService.delete('userId');
        this.router.navigate(['/register']);
      });
  }

  joinGame(gameCode: string) {
    this.restService.joinGame(gameCode)
      .then(() => this.goToLobby())
      .catch(() => this.lobbyError = true);
  }

  ngOnInit() {
    if (this.cookieService.check('gameCode')) {
      this.goToLobby();
    }
    this.getGames();
  }

}
