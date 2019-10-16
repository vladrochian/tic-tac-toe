import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {GameListComponent} from './game-list/game-list.component';
import {RouterModule, Routes} from '@angular/router';
import {GameComponent} from './game/game.component';
import {LobbyComponent} from './lobby/lobby.component';
import {RegisterComponent} from './register/register.component';
import {CookieService} from 'ngx-cookie-service';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RestService} from './rest/rest.service';

const appRoutes: Routes = [
  {path: 'register', component: RegisterComponent},
  {path: 'game-list', component: GameListComponent},
  {path: 'lobby', component: LobbyComponent},
  {path: 'game', component: GameComponent},
  {path: '**', redirectTo: '/register', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    GameListComponent,
    GameComponent,
    LobbyComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    CookieService,
    RestService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
