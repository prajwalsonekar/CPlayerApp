import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../Service/authentication.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent  {
  

  nameCtrl : FormControl;
  
  passwordCtrl : FormControl;
  errMsg:string|undefined;
 
  myform : FormGroup;
  constructor(builder: FormBuilder,
    private authService: AuthenticationService,
    private router: Router) {
    this.nameCtrl  = builder.control('', [
      Validators.required,
      Validators.minLength(4)]);

    this.passwordCtrl =  builder.control('', [
      Validators.required,
      Validators.minLength(7)]);
    
    const mapValues ={
      Username : this.nameCtrl,
     
      Password : this.passwordCtrl,
      

     
    }
    this.myform = builder.group(mapValues);
    
  }
  
  addUser(){
    
    let name = this.nameCtrl.value;
  
    let password = this.passwordCtrl.value;
    if (!this.myform.valid) {
      this.myform.markAllAsTouched();
      return;
    }
 
   

    
    const observer = {
      next: (token: string) => {
        
        console.log('received token', token);
        this.errMsg = undefined;
        alert("Registered Successfully");
        this.authService.saveToken(name, token);
        
        console.log("executed");
      },

      error: (err: Error) => {
        this.errMsg = err.message;
        console.log("error",err);
        
      },
    };
    const observable:Observable<string>=this.authService.signup(name,password);
    observable.subscribe(observer);
  
    }
    isTouchedOrDirty(control: FormControl): boolean {
      return control.touched || control.dirty;
    }
    isNameCtrlRequireValid() {
      const touchedOrDirty=this.isTouchedOrDirty(this.nameCtrl);
      if (!touchedOrDirty) {
        return true;
      }
      return !this.nameCtrl.errors?.['required'];
    }
  
    isNameCtrlMinLengthValid() {
      const touchedOrDirty=this.isTouchedOrDirty(this.nameCtrl);
      if (!touchedOrDirty) {
        return true;
      }
      return !this.nameCtrl.errors?.['minlength'];
    }

    isPasswordCtrlRequireValid():boolean {
      const touchedOrDirty=this.isTouchedOrDirty(this.passwordCtrl);
      if(!touchedOrDirty){
        return true;
      }
      return   !this.passwordCtrl.errors?.['required'];
    }
  
    isPasswordCtrlMinValid():boolean {
      const touchedOrDirty=this.isTouchedOrDirty(this.passwordCtrl);
      if(!touchedOrDirty){
        return true;
      }
      return !this.passwordCtrl.errors?.['minlength'];
    }
  
  }
  


