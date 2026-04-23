import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ParticipantDto } from '../models/participant.dto';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private apiUrl = 'http://localhost:8080/summoner';

  constructor(private http: HttpClient) {}

  getLastGamePlayer(name: string, tag: string): Observable<ParticipantDto> {
    return this.http.get<ParticipantDto>(`${this.apiUrl}/${name}/${tag}/lastgame/player`);
  }

  getLastGameFriends(): Observable<ParticipantDto[]> {
    return this.http.get<ParticipantDto[]>(`${this.apiUrl}/friends/lastgame`);
  }
}
