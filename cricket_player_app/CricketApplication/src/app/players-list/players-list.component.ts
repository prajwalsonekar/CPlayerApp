import { ViewEncapsulation } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";
import { PlayerInfo } from 'src/model/PlayerInfo';
import { favoritePlayers, PlayerData } from '../util/constant';
import { PlayerUtil } from '../util/player.util';
import { Observable } from 'rxjs';
import { FavouritePlayersService } from '../Service/favourite-players.service';




@Component({
  selector: 'app-player-list',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './players-list.component.html',
  styleUrls: ['./players-list.component.css']
})
export class PlayersListComponent implements OnInit {

  
  playerUtil: PlayerUtil = new PlayerUtil();
  public players: PlayerInfo[] = favoritePlayers;

  constructor(private titleService: Title ,private service : FavouritePlayersService) {
  
  }

  ngOnInit(): void {
    this.titleService.setTitle("Players List");
    document.body.className = "selector";
    let username=localStorage.getItem("username")
    let observable: Observable<PlayerInfo[]> = this.service.listofFavorite(username);
    const observer={
      next:(result:any)=>{
        this.players = result;
      },
      error:(e:Error)=>{
        console.log('error received', e.message);
      }    
    } 
       observable.subscribe(observer);
  }
  ngOnDestroy() {
    this.titleService.setTitle("CricketApplication");
    document.body.className = "";
  }

 
  removeItem(item :PlayerInfo){
 
    
    let idGotFromPlayer :string  |undefined = item.externalServerId;
    if(idGotFromPlayer == undefined){
      return ;
    }   
     let observable: Observable<void> = this.service.removeFavorite(idGotFromPlayer);
    const observer={
      next:(result : any )=>{
        this.ngOnInit();
      },
      error:(e:Error)=>{
        console.log('error received', e.message);
      }    
    } 
       observable.subscribe(observer);

  }

}
