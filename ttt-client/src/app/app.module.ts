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
import { HeaderComponent } from './header/header.component';
import {
  MatButtonModule, MatCardModule,
  MatCheckboxModule,
  MatFormFieldModule, MatGridListModule,
  MatIconModule,
  MatInputModule, MatListModule,
  MatProgressSpinnerModule,
  MatToolbarModule
} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

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
    RegisterComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatGridListModule,
    MatListModule
  ],
  providers: [
    CookieService,
    RestService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
