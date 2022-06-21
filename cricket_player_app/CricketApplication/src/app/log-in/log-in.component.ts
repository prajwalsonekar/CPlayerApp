import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../Service/authentication.service';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {
  myform: FormGroup;
  usernameCtrl: FormControl;
  passwordCtrl: FormControl;
  errMsg: string | undefined;
  loggedIn: boolean

  constructor(builder: FormBuilder,
    private authService: AuthenticationService,
    private router: Router) {
    this.usernameCtrl = builder.control('');
    this.passwordCtrl = builder.control('');
    let mapValues = {
      Username: this.usernameCtrl,
      Password: this.passwordCtrl
    }
    this.myform = builder.group(mapValues);
  }

  checkUser() {
    console.log("User added Login");
    let name = this.usernameCtrl.value;
    let password = this.passwordCtrl.value;
    console.log("Username; " + name + " Password" + password);
    const observer = {
      next: (token: string) => {
        this.loggedIn = true;
        alert("Logged in Successfully");
        console.log('received token', token);
        this.errMsg = undefined;
        this.authService.saveToken(name, token);
        this.router.navigate(["/dashboard"]);
        console.log("executed");
      },

      error: (err: Error) => {
        this.errMsg = err.message;
        console.log("error", err);

      },
    };
    const observable: Observable<string> = this.authService.login(name, password);

    observable.subscribe(observer);
  }


  ngOnInit(): void {

  }

  movePage(pageName: string) {
    this.router.navigate([`${pageName}`]);
  }
}
