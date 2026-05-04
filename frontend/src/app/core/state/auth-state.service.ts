import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthStateService {
  private _isLoggedIn = signal<boolean>(this.hasToken());

  isLoggedIn = this._isLoggedIn.asReadonly();

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  setToken(token: string) {
    localStorage.setItem('token', token);
    this._isLoggedIn.set(true);
  }

  logout() {
    localStorage.removeItem('token');
    this._isLoggedIn.set(false);
  }
}
