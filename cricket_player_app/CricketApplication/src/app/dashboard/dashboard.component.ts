import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { OwlModule } from 'ngx-owl-carousel';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  title: string = 'Home';

  constructor(private titleService: Title) { }

 
  ngOnInit(): void {
    this.titleService.setTitle(this.title);
  }
  ngOnDestroy() {
    this.titleService.setTitle("CricketApplication");
  }

}
