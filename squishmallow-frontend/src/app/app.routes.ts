import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeComponent } from './_component/welcome/welcome.component';
import { SquishmallowListComponent } from './_component/squishmallow/squishmallow-list/squishmallow-list.component';
import { UserListComponent } from './_component/user/user-list/user-list.component';


export const routes: Routes = [
  { path: 'welcome', component: WelcomeComponent },
  { path: 'squishmallow', component: SquishmallowListComponent },
  { path: 'user-list', component: UserListComponent },
  { path: '**', redirectTo: 'welcome' }
];
