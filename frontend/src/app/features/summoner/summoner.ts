import { Component, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RiotService } from '../../../core/services/riot.service';
import { finalize } from 'rxjs';

import { FriendGameDetailDto } from '../../../core/models/friend-game-details.dto';
import { SummonerGameDetailsDto } from '../../../core/models/summoner-game-details.dto';

@Component({
  selector: 'app-summoner',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './summoner.html',
  styleUrls: ['./summoner.css'],
})
export class SummonerComponent implements OnInit {
  constructor(private riotService: RiotService) {}

  name = signal('');
  tag = signal('');

  loading = signal(false);
  error = signal('');
  result = signal<SummonerGameDetailsDto | null>(null);
  friends = signal<FriendGameDetailDto[]>([]);

  ngOnInit() {
    this.riotService.getFriendsGames().subscribe({
      next: (data) => this.friends.set(data),
      error: () => this.error.set('Erreur chargement friends'),
    });
  }

  search() {
    if (!this.name() || !this.tag()) return;

    this.loading.set(true);
    this.error.set('');

    this.riotService
      .getPlayerWithGame(this.name(), this.tag())
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (data) => this.result.set(data),
        error: () => this.error.set('Erreur API'),
      });
  }
}
