import {Injectable} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class RestService {
  private serverHost = window.location.hostname;
  private serverUrl = 'http://' + this.serverHost + ':8080';
  private options = {headers: new Headers({'Content-Type': 'application/json'})};

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
    return this.http.get(this.serverUrl + '/lobby/games').toPromise();
  }

  createGame(game) {
    return this.http.post(this.serverUrl + '/lobby/games', game, {responseType: 'text'}).toPromise()
      .then((code: string) => this.cookieService.set('gameCode', code));
  }
}