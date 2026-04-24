import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { finalize } from 'rxjs';

import { GameService } from '../../core/services/game.service';
import { FriendService } from '../../core/services/friend.service';
import { ParticipantDto } from '../../core/models/participant.dto';

import { GamePreviewComponent } from '../../shared/components/game-preview/game-preview';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [CommonModule, FormsModule, GamePreviewComponent],
  templateUrl: './home-page.html',
  styleUrls: ['./home-page.css'],
})
export class HomePageComponent {
  constructor(
    private gameService: GameService,
    private friendService: FriendService,
    private router: Router,
  ) {}

  name = signal('');
  tag = signal('');

  isFriend = signal(false);

  validName = '';
  validTag = '';

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
        next: (data) => {
          this.result.set(data);

          this.friendService.alreadyExists(this.name(), this.tag()).subscribe((exists) => {
            this.isFriend.set(exists);
          });

          this.validName = this.name();
          this.validTag = this.tag();
        },
        error: () => this.error.set('Erreur API'),
      });
  }

  goToDetail(player: ParticipantDto) {
    this.router.navigate(['/game', player.puuid]);
  }

  toggleFriend(name: string, tag: string) {
    if (this.isFriend()) {
      this.friendService.removeFriend(name, tag).subscribe({
        next: () => {
          this.isFriend.set(false);
          console.log('friend removed');
        },
      });
    } else {
      this.friendService.addFriend(name, tag).subscribe({
        next: () => {
          this.isFriend.set(true);
          console.log('friend added');
        },
      });
    }
  }
}
