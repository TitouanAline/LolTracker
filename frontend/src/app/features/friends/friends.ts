import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RiotService } from '../../../core/services/riot.service';
import { FriendGameDetailDto } from '../../../core/models/friend-game-details.dto';

@Component({
  selector: 'app-friends',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './friends.html',
  styleUrls: ['./friends.css'],
})
export class FriendsComponent implements OnInit {
  constructor(private riotService: RiotService) {}

  friends = signal<FriendGameDetailDto[]>([]);
  loading = signal(false);
  error = signal('');

  ngOnInit() {
    this.loading.set(true);

    this.riotService.getFriendsGames().subscribe({
      next: (data) => this.friends.set(data),
      error: () => this.error.set('Erreur chargement friends'),
      complete: () => this.loading.set(false),
    });
  }
}
