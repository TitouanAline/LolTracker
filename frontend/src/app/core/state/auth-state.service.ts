import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthStateService {
  private _isLoggedIn = signal<boolean>(this.hasToken());

  isLoggedIn = this._isLoggedIn.asReadonly();

  private _user = signal<any>(null);
  user = this._user.asReadonly();

  constructor(private http: HttpClient) {}

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  loadUser() {
    this.http.get('http://localhost:8080/auth/me').subscribe({
      next: (user) => this._user.set(user),
      error: () => {
        this.logout();
      },
    });
  }

  setToken(token: string) {
    localStorage.setItem('token', token);
    this._isLoggedIn.set(true);
    this.loadUser();
  }

  logout() {
    localStorage.removeItem('token');
    this._isLoggedIn.set(false);
    this._user.set(null);
  }

  init() {
    const token = localStorage.getItem('token');

    if (!token) return;

    this._isLoggedIn.set(true);
    this.loadUser();
  }
}
