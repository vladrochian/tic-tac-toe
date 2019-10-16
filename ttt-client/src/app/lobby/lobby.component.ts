import {Component, OnInit} from '@angular/core';
import {RestService} from '../rest/rest.service';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css']
})
export class LobbyComponent implements OnInit {
  timeout = null;
  game: {
    hostName: string,
    playerNames: string[]
    myGame: boolean,
    started: boolean
  } = null;

  constructor(private restService: RestService, private router: Router, private cookieService: CookieService) {
  }

  getLobby() {
    this.restService.getLobby()
      .then((game: any) => {
        this.game = game;
        if (game.started) {
          this.router.navigate(['/game']);
        } else {
          this.timeout = setTimeout(() => this.getLobby(), 1000);
        }
      })
      .catch(() => this.backToList());
  }

  ngOnInit() {
    this.getLobby();
  }

  backToList() {
    clearTimeout(this.timeout);
    this.cookieService.delete('gameCode');
    this.router.navigate(['/game-list']);
  }

  changeSides() {
    // TODO: Rocky
  }

  startGame() {
    this.restService.startGame()
      .then(() => this.router.navigate(['/game']))
      .catch(() => this.backToList());
  }

  leaveGame() {
    this.restService.leaveGame()
      .then(() => this.backToList())
      .catch(() => this.backToList());
  }
}
