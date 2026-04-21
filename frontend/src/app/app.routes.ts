import { Routes } from '@angular/router';
import { SummonerComponent } from './features/summoner/summoner';
import { FriendsComponent } from './features/friends/friends';

export const routes: Routes = [
  { path: '', component: SummonerComponent },
  { path: 'friend', component: FriendsComponent },
];
