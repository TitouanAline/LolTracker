import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RiotService } from '../../core/services/riot.service';
import { OnInit } from '@angular/core';
import { BehaviorSubject, switchMap, map, catchError, of, tap, forkJoin, Observable } from 'rxjs';
import { FriendGameDetailDto } from '../../core/models/friend-game-details.dto';

@Component({
  selector: 'app-summoner',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './summoner.html',
  styleUrls: ['./summoner.css'],
})
export class SummonerComponent implements OnInit {
  friends$!: Observable<FriendGameDetailDto[]>;

  constructor(private riotService: RiotService) {}

  ngOnInit(): void {
    this.friends$ = this.riotService.getFriendsGames();
  }

  name = '';
  tag = '';

  loading = false;
  error = '';

  private searchTrigger = new BehaviorSubject<{ name: string; tag: string } | null>(null);

  result$ = this.searchTrigger.pipe(
    tap(() => {
      this.loading = true;
      this.error = '';
    }),

    switchMap((params) => {
      if (!params) return of(null);

      return this.riotService.getPlayerWithGame(params.name, params.tag).pipe(
        catchError(() => {
          this.error = 'Erreur API';
          return of(null);
        }),
      );
    }),

    tap(() => (this.loading = false)),
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
