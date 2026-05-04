import { Routes } from '@angular/router';
import { HomePageComponent } from './features/home-page/home-page';
import { FriendsPageComponent } from './features/friends-page/friends-page';
import { SummonerDetailedPageComponent } from './features/summoner-detailed-page/summoner-detailed-page';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'friends', component: FriendsPageComponent },
  { path: 'game/:name/:tag', component: SummonerDetailedPageComponent },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
];
