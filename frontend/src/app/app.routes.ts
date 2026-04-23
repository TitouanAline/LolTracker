import { Routes } from '@angular/router';
import { HomePageComponent } from './features/home-page/home-page';
import { FriendsComponent } from './features/friends/friends';
import { FriendsDetailComponent } from './features/friend-detail/friend-detail';

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'friends', component: FriendsComponent },
  { path: 'friend/:puuid', component: FriendsDetailComponent },
];
