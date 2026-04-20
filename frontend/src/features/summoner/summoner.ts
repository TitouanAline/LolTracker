import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RiotService } from '../../core/services/riot.service';
import { BehaviorSubject, switchMap, map, catchError, of, tap, forkJoin } from 'rxjs';

type FriendResult = {
  name: string;
  tag: string;
  champion: string | null;
  championImage: string | null;
  splashImage: string | null;
  kills: number | null;
  deaths: number | null;
  assists: number | null;
  win: boolean | null;
  error?: boolean;
};

@Component({
  selector: 'app-summoner',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './summoner.html',
  styleUrls: ['./summoner.css'],
})
export class SummonerComponent {
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

      return this.riotService.getAccountPuuid(params.name, params.tag).pipe(
        switchMap((account: any) => this.riotService.getMatchDetails(account.puuid, 0)),

        map((data: any) => this.formatPlayer(data)),

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
    switchMap((friends) => {
      return Promise.all(
        friends.map(async (f): Promise<FriendResult> => {
          try {
            const account: any = await this.riotService.getAccountPuuid(f.name, f.tag).toPromise();
            const match: any = await this.riotService.getMatchDetails(account.puuid, 0).toPromise();

            return {
              name: f.name,
              tag: f.tag,
              champion: match.champion,
              championImage: `https://ddragon.leagueoflegends.com/cdn/13.1.1/img/champion/${match.champion}.png`,
              splashImage: `https://ddragon.leagueoflegends.com/cdn/img/champion/splash/${match.champion}_0.jpg`,
              kills: match.kills,
              deaths: match.deaths,
              assists: match.assists,
              win: match.win,
              error: false,
            };
          } catch (e) {
            // 👇 TRÈS IMPORTANT : même structure !
            return {
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
            };
          }
        }),
      );
    }),
  );

  constructor(private riotService: RiotService) {}

  // 🔥 FACTORISATION (très important)
  formatPlayer(data: any) {
    return {
      champion: data.champion,
      championImage: `https://ddragon.leagueoflegends.com/cdn/13.1.1/img/champion/${data.champion}.png`,
      splashImage: `https://ddragon.leagueoflegends.com/cdn/img/champion/splash/${data.champion}_0.jpg`,
      kills: data.kills,
      deaths: data.deaths,
      assists: data.assists,
      win: data.win,
    };
  }

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
