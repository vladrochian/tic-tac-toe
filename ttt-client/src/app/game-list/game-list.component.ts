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
  newGame = {
    hostId: this.cookieService.get('userId'),
    tableHeight: 3,
    tableWidth: 3,
    lineSize: 3,
    gamePublic: true
  };
  games = [];

  constructor(private cookieService: CookieService, private restService: RestService, private router: Router) {
  }

  createGame() {
    this.restService.createGame(this.newGame)
      .then(() => this.router.navigate(['/lobby']));
  }

  ngOnInit() {
    if (this.cookieService.check('gameCode')) {
      this.router.navigate(['/lobby']);
    }
    this.restService.getPublicGames()
      .then((games: any) => this.games = games);
  }

}
