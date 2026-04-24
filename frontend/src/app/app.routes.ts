import { Routes } from '@angular/router';
import { HomePageComponent } from './features/home-page/home-page';
import { FriendsPageComponent } from './features/friends-page/friends-page';
import { SummonerDetailedPageComponent } from './features/summoner-detailed-page/summoner-detailed-page';

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'friends', component: FriendsPageComponent },
  { path: 'game/:name/:tag', component: SummonerDetailedPageComponent },
];
