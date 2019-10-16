import { Component, OnInit } from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';
import {RestService} from '../rest/rest.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  name = '';

  constructor(private cookieService: CookieService, private router: Router, private restService: RestService) { }

  ngOnInit() {
    if (this.cookieService.get('userId')) {
      this.navigate();
    }
  }

  register(): void {
    this.restService.register(this.name).then(() => this.navigate());
  }

  navigate(): void {
    this.router.navigate(['/game-list']);
  }

}
