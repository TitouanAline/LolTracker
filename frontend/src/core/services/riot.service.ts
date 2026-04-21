import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, forkJoin, map, Observable, of, switchMap } from 'rxjs';

import { SummonerDto } from '../models/summoner.dto';
import { SummonerGameDetailsDto } from '../models/summoner-game-details.dto';
import { FriendGameDetailDto } from '../models/friend-game-details.dto';

@Injectable({
  providedIn: 'root',
})
export class RiotService {
  private apiUrl = 'http://localhost:8080/summoner';

  constructor(private http: HttpClient) {}

  getSummoner(name: string, tag: string): Observable<SummonerDto> {
    return this.http.get<SummonerDto>(`${this.apiUrl}/${name}/${tag}`);
  }

  getGame(puuid: string, index: number): Observable<SummonerGameDetailsDto> {
    return this.http.get<SummonerGameDetailsDto>(`${this.apiUrl}/${puuid}/game/${index}`);
  }

  getPlayerWithGame(name: string, tag: string) {
    return this.getSummoner(name, tag).pipe(switchMap((account) => this.getGame(account.puuid, 0)));
  }

  getFriendsGames() {
    return this.http.get<FriendGameDetailDto[]>(`${this.apiUrl}/friends/games`);
  }
}
