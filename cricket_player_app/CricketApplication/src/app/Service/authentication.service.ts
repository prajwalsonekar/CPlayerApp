import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { baseServerUrl } from '../constants';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private client:HttpClient) { }

  isUserLoggedIn():boolean{
    const token=this.getToken();
    return token!=undefined && token!=null ;  
}

getUsername():string{
  const username=localStorage.getItem("username");
  return username;
}
getToken():string{
  const token=localStorage.getItem('token');
  return token;
}

login(username:string, password:string):Observable<string>{
  const url=baseServerUrl+"/login";
  console.log("inside login");
  const requestData={username,password};
  const observable:Observable<string>=this.client.post(url,requestData,{responseType:"text"});
     return observable;
}

signup(username:string, password:string):Observable<string>{
  const url=baseServerUrl+"/register";
  console.log("inside register");
  const requestData={username,password};
  const observable:Observable<string>=this.client.post(url,requestData,{responseType:"text"});
  console.log(observable);
  return observable;
}
logout():void{
  localStorage.removeItem('token');
  localStorage.removeItem('username');
}

saveToken(username:string,token:string){
  localStorage.setItem('username',username); 
  localStorage.setItem('token',token);
 }

}
