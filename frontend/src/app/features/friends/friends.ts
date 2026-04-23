import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RiotService } from '../../core/services/riot.service';
import { FriendGameDetailDto } from '../../core/models/friend-game-details.dto';
import { Router } from '@angular/router';

import { GamePreviewComponent } from '../../shared/components/game-preview/game-preview';
import { ParticipantDto } from '../../core/models/participant.dto';
import { GameService } from '../../core/services/game.service';

@Component({
  selector: 'app-friends',
  standalone: true,
  imports: [CommonModule, GamePreviewComponent],
  templateUrl: './temp.html',
  styleUrls: ['./temp.css'],
})
export class FriendsComponent implements OnInit {
  constructor(
    private gameService: GameService,
    private router: Router,
  ) {}

  friends = signal<ParticipantDto[]>([]);
  loading = signal(false);
  error = signal('');

  ngOnInit() {
    this.loading.set(true);

    this.gameService.getLastGameFriends().subscribe({
      next: (data) => this.friends.set(data),
      error: () => this.error.set('Erreur chargement friends'),
      complete: () => this.loading.set(false),
    });
  }

  formatNumber(value: number | null): String | null {
    if (value == null) {
      return null;
    }
    if (value >= 1000) {
      return (value / 1000).toFixed(1).replace('.0', '') + 'k';
    }
    return value.toString();
  }
}
