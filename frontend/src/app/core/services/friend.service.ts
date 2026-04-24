import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FriendService {
  private apiUrl = 'http://localhost:8080/friends';

  constructor(private http: HttpClient) {}

  addFriend(name: string, tag: string) {
    return this.http.post(this.apiUrl, { name, tag });
  }

  alreadyExists(name: string, tag: string) {
    return this.http.get<boolean>(`${this.apiUrl}/doAlreadyExists/${name}/${tag}`);
  }

  removeFriend(name: string, tag: string) {
    return this.http.delete(`${this.apiUrl}/remove/${name}/${tag}`);
  }
}
