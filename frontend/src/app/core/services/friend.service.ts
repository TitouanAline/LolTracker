import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FriendService {
  private apiUrl = `${environment.apiUrl}/friends`;

  constructor(private http: HttpClient) {}

  addFriend(name: string, tag: string) {
    return this.http.post(this.apiUrl + '/add', { name, tag });
  }

  alreadyExists(name: string, tag: string) {
    return this.http.get<boolean>(`${this.apiUrl}/doAlreadyExists/${name}/${tag}`);
  }

  removeFriend(name: string, tag: string) {
    return this.http.delete(`${this.apiUrl}/remove/${name}/${tag}`);
  }
}
