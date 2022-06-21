import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './Service/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'CricketApplication';
  
  constructor(private authService:AuthenticationService, private router:Router){
    
  }

  isLoggedIn():boolean{
  return this.authService.isUserLoggedIn();
  }

 
}
