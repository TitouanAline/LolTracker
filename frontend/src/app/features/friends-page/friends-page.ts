import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GamePreviewComponent } from '../../shared/components/game-preview/game-preview';
import { ParticipantDto } from '../../core/models/participant.dto';
import { GameService } from '../../core/services/game.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-friends',
  standalone: true,
  imports: [CommonModule, GamePreviewComponent],
  templateUrl: './friends-page.html',
  styleUrls: ['./friends-page.css'],
})
export class FriendsPageComponent implements OnInit {
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

  goToDetail(player: ParticipantDto) {
    this.router.navigate(['/game', player.name, player.tag]);
  }
}
