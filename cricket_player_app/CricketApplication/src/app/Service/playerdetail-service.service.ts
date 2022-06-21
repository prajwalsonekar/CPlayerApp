import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PlayerData } from '../util/constant';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlayerdetailServiceService {

  constructor(private client :HttpClient) { 
    
  }

  public  getIdFromPlayerName(firstName :String ,lastName :String ): Observable<PlayerData> {
    let url: string  =  "https://api.cricapi.com/v1/players?apikey=ae61c875-e187-411c-b3c7-dc05f820af56&offset=0&search="+firstName+"%20"+lastName;
    let observable: Observable<PlayerData>=  this.client.get<PlayerData>(url);
   return observable;
  }


  public getDetailsFromPlayerId(id: string ):Observable<PlayerData>{
    
    let idGot = id.substring(1, id.length-1);
    let url = "https://api.cricapi.com/v1/players_info?apikey=ae61c875-e187-411c-b3c7-dc05f820af56&id="+idGot;
    let observable: Observable<PlayerData>  =  this.client.get<PlayerData>(url);
    return observable;
  }

}
