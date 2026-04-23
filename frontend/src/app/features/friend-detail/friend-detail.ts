import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RiotService } from '../../core/services/riot.service';
import {} from '../../core/models/friend-game-details.dto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-friend-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './friend-detail.html',
  styleUrl: './friend-detail.css',
})
export class FriendsDetailComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private riotService: RiotService,
  ) {}

  ngOnInit() {
    const puuid = this.route.snapshot.paramMap.get('puuid');

    if (puuid) {
      this.riotService.getGame(puuid, 0).subscribe((data) => {});
    }
  }
}
