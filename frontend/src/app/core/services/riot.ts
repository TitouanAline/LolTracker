// riot.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Summoner {
  name: string;
  summonerLevel: number;
}

@Injectable({
  providedIn: 'root'
})
export class RiotService {
  private apiUrl = 'http://localhost:8080/summoner';

  constructor(private http: HttpClient) {}

  // récupérer compte (name + tag)
  getAccount(name: string, tag: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${name}/${tag}`);
  }

  // récupérer dernier champion
  getLastChampion(puuid: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/last-game/${puuid}`);
  }
}