import {Injectable} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class RestService {
  private serverHost = window.location.hostname;
  private serverUrl = 'http://' + this.serverHost + ':8080';

  constructor(private cookieService: CookieService, private http: HttpClient) {
  }

  private get userId(): string {
    return this.cookieService.get('userId');
  }

  private set userId(value: string) {
    this.cookieService.set('userId', value);
  }

  register(name: string) {
    return this.http.post(this.serverUrl + '/registration/players', name, {responseType: 'text'}).toPromise()
      .then((id: string) => this.userId = id);
  }

  getPublicGames() {
    return this.http.get(this.serverUrl + '/lobby/games/' + this.userId).toPromise();
  }

  createGame(game) {
    return this.http.post(this.serverUrl + '/lobby/games', game, {responseType: 'text'}).toPromise()
      .then((code: string) => this.cookieService.set('gameCode', code));
  }

  joinGame(gameCode: string) {
    return this.http.post(this.serverUrl + '/lobby/games/' + gameCode + '/opponent', this.userId).toPromise()
      .then(() => this.cookieService.set('gameCode', gameCode));
  }

  getLobby() {
    return this.http.get(this.serverUrl + '/lobby/my-game/' + this.userId).toPromise();
  }

  startGame() {
    return this.http.put(this.serverUrl + '/lobby/my-game/' + this.userId + '/status', {}).toPromise();
  }

  kickOpponent() {
    return this.http.delete(this.serverUrl + '/lobby/my-game/' + this.userId + '/opponent').toPromise();
  }

  changeSides() {
    return this.http.put(this.serverUrl + '/lobby/my-game/' + this.userId + '/opponent', {}).toPromise();
  }

  leaveGame() {
    return this.http.delete(this.serverUrl + '/lobby/my-game/' + this.userId).toPromise();
  }

  getActiveGame() {
    return this.http.get(this.serverUrl + '/active-game/' + this.userId).toPromise();
  }

  hasOpponentMoved() {
    return this.http.get(this.serverUrl + '/active-game/' + this.userId + '/move', {responseType: 'text'}).toPromise();
  }

  performMove(row: number, column: number) {
    return this.http.post(this.serverUrl + '/active-game/' + this.userId + '/move', {row, column}, {responseType: 'text'}).toPromise();
  }
}
