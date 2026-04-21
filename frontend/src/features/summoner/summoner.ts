import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RiotService } from '../../core/services/riot.service';
import { BehaviorSubject, switchMap, map, catchError, of, tap, forkJoin } from 'rxjs';
import { SummonerDto } from '../../core/models/summoner.dto';
import { SummonerGameDetailsDto } from '../../core/models/summoner-game-details.dto';
import { FriendGameDetailDto } from '../../core/models/friend-game-details.dto';

@Component({
  selector: 'app-summoner',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './summoner.html',
  styleUrls: ['./summoner.css'],
})
export class SummonerComponent {
  constructor(private riotService: RiotService) {}

  name = '';
  tag = '';

  loading = false;
  error = '';

  // 🔥 trigger
  private searchTrigger = new BehaviorSubject<{ name: string; tag: string } | null>(null);

  // 🔥 RESULT PRINCIPAL
  result$ = this.searchTrigger.pipe(
    tap(() => {
      this.loading = true;
      this.error = '';
    }),

    switchMap((params) => {
      if (!params) return of(null);

      return this.riotService.getSummoner(params.name, params.tag).pipe(
        switchMap((account) => this.riotService.getGame(account.puuid, 0)),

        map((data) => data),

        catchError((err) => {
          console.error(err);
          this.error = 'Erreur API';
          return of(null);
        }),
      );
    }),

    tap(() => (this.loading = false)),
  );

  // 🔥 FRIENDS LIST
  friends = [
    { name: 'Fyralll', tag: '5403' },
    { name: 'G2 SkewMond', tag: '3327' },
    { name: 'G2 Caps', tag: '1323' },
    { name: 'Nathanzor', tag: 'EUW' },
  ];

  friends$ = of(this.friends).pipe(
    switchMap((friends) =>
      forkJoin(
        friends.map((f) =>
          this.riotService.getSummoner(f.name, f.tag).pipe(
            switchMap((account) => this.riotService.getGame(account.puuid, 0)),
            map(
              (match): FriendGameDetailDto => ({
                name: f.name,
                tag: f.tag,
                champion: match.champion,
                championImage: match.championIcon,
                splashImage: match.championSplashArt,
                kills: match.kills,
                deaths: match.deaths,
                assists: match.assists,
                win: match.win,
                error: false,
              }),
            ),
            catchError(() =>
              of({
                name: f.name,
                tag: f.tag,
                champion: null,
                championImage: null,
                splashImage: null,
                kills: null,
                deaths: null,
                assists: null,
                win: null,
                error: true,
              }),
            ),
          ),
        ),
      ),
    ),
  );
  search() {
    console.log('CLICK OK');

    if (!this.name || !this.tag) {
      console.log('❌ Champs vides');
      return;
    }

    this.searchTrigger.next({
      name: this.name,
      tag: this.tag,
    });
  }
}
