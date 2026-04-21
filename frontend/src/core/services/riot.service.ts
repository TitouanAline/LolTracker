import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SummonerDto } from '../models/summoner.dto';
import { SummonerGameDetailsDto } from '../models/summoner-game-details.dto';

@Injectable({
  providedIn: 'root',
})
export class RiotService {
  private apiUrl = 'http://localhost:8080/summoner';

  constructor(private http: HttpClient) {}

  getSummoner(name: string, tag: string): Observable<SummonerDto> {
    return this.http.get<SummonerDto>(`${this.apiUrl}/${name}/${tag}`);
  }

  // récupérer dernier champion
  getGame(puuid: string, index: number): Observable<SummonerGameDetailsDto> {
    return this.http.get<SummonerGameDetailsDto>(`${this.apiUrl}/${puuid}/game/${index}`);
  }
}
