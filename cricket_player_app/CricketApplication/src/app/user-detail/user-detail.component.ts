import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  name : string ="Wipro";
  email : string ="velocity.helpdesk@gmail.com";
  training : string ="stackroute";
  phoneNumber : string ="08030275351";
  
  constructor() { }

  ngOnInit(): void {
  }
}
