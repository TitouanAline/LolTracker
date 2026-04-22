import { Routes } from '@angular/router';
import { SummonerComponent } from './features/summoner/summoner';
import { FriendsComponent } from './features/friends/friends';
import { FriendsDetailComponent } from './features/friend-detail/friend-detail';

export const routes: Routes = [
  { path: '', component: SummonerComponent },
  { path: 'friends', component: FriendsComponent },
  { path: 'friend/:puuid', component: FriendsDetailComponent },
];
