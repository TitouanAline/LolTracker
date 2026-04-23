import { Routes } from '@angular/router';
import { HomePageComponent } from './features/home-page/home-page';
import { FriendsPageComponent } from './features/friends-page/friends-page';
import { FriendsDetailComponent } from './features/friend-detail/friend-detail';

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'friends', component: FriendsPageComponent },
  { path: 'game/:puuid', component: FriendsDetailComponent },
];
