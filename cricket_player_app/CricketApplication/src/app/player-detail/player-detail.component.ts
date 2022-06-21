import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ViewEncapsulation } from '@angular/core';
import { PlayerUtil } from '../util/player.util';
import { PlayerInfo } from 'src/model/PlayerInfo';
import { favoritePlayers, PlayerData, playersResult, result1 } from '../util/constant';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { LogInComponent } from '../log-in/log-in.component';
import { PlayerdetailServiceService } from '../Service/playerdetail-service.service';
import { Observable } from 'rxjs';
import { FavouritePlayersService } from '../Service/favourite-players.service';
import { AuthenticationInterceptor } from '../request.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

@Component({
  selector: 'app-player-detail',
  templateUrl: './player-detail.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./player-detail.component.css']
})
export class PlayerDetailComponent implements OnInit {
 
  playerUtil: PlayerUtil = new PlayerUtil();
  playerInfo: PlayerInfo | undefined;
  playerData : PlayerData | undefined;
  

  nameCtrl: FormControl;
  searchform: FormGroup;
  constructor(builder: FormBuilder, private titleService: Title ,private servicePlayerDetail  : PlayerdetailServiceService , private serviceFavorite : FavouritePlayersService ) {
    this.nameCtrl = builder.control('');
    const mapping = { searchTerm: this.nameCtrl };
    this.searchform = builder.group(mapping);
  }

  fetchPlayer() {
    let searchTerm: string = this.nameCtrl.value;
    searchTerm = searchTerm.trim();
    searchTerm = searchTerm.toLowerCase();
    if (searchTerm && searchTerm.length > 0) {
      let values :string[] = searchTerm.split(" ");
      let observable : Observable<PlayerData>  = this.servicePlayerDetail.getIdFromPlayerName(values[0],values[1]); 
      
      const observer={
        next:(result:any)=>{
         let id :string=JSON.stringify(result.data[0].id);
          this.fetchDetails(id);
        },
        error:(e:Error)=>{
          console.log('error received', e.message);
        }    
      } 
      observable.subscribe(observer);
    }
  }

  // fetching player details from id
  fetchDetails(id :any ){
    
    let observable : Observable<PlayerData>  = this.servicePlayerDetail.getDetailsFromPlayerId(id);
    const observer={
      next:(result:any)=>{
        this.playerInfo = this.playerUtil.convertToPlayer(result.data);
      },
      error:(e:Error)=>{
        console.log('error received', e.message);
      }    
    } 
       observable.subscribe(observer);
  }



  ngOnInit(): void {
    this.titleService.setTitle("Player Detail");
    document.body.className = "selector1";
  }
 
  addTofavourite(plays :any){
    let observable : Observable<PlayerData> = this.serviceFavorite.addToFavorite(plays);
    const observer={
      next:(result:any)=>{
        alert("Player Added");
       
      },
      error:(e:Error)=>{
        console.log('error received', e.message);
      }    
    } 
    observable.subscribe(observer);
  }
}
