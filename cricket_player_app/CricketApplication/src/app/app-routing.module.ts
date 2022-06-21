import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginGuard,  } from './guards/login.guard';
import { LogInComponent } from './log-in/log-in.component';
import { PlayerDetailComponent } from './player-detail/player-detail.component';
import { PlayersListComponent } from './players-list/players-list.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
const routes: Routes = [
  {
    path: 'dashboard', component: DashboardComponent
  },
  {
     path: '',   redirectTo: '/dashboard', pathMatch: 'full' 
  },
  {
    path: 'players-list',  component: PlayersListComponent, canActivate:[LoginGuard]
  }, 
  {
    path: 'log-in',  component: LogInComponent
  }, 
  {
    path: 'sign-up',  component: SignUpComponent
  },  
  {
    path: 'user-detail',  component: UserDetailComponent
  }, 
  {
    path: 'player-detail',  component: PlayerDetailComponent, canActivate:[LoginGuard]
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
