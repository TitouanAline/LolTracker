import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { finalize } from 'rxjs';

import { GameService } from '../../core/services/game.service';
import { ParticipantDto } from '../../core/models/participant.dto';

@Component({
  selector: 'app-summoner',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './temp.html',
  styleUrls: ['./temp.css'],
})
export class SummonerComponent {
  constructor(private gameService: GameService) {}

  name = signal('');
  tag = signal('');

  loading = signal(false);
  error = signal('');
  result = signal<ParticipantDto | null>(null);

  search() {
    if (!this.name() || !this.tag()) return;

    this.loading.set(true);
    this.error.set('');

    this.gameService
      .getLastGamePlayer(this.name(), this.tag())
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (data) => this.result.set(data),
        error: () => this.error.set('Erreur API'),
      });
  }

  formatNumber(value: number | null): string {
    if (!value) return '0';
    if (value >= 1000) {
      return (value / 1000).toFixed(1).replace('.0', '') + 'k';
    }
    return value.toString();
  }
}
