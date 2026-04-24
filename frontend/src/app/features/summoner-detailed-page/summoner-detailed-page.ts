import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {} from '../../core/models/friend-game-details.dto';
import { CommonModule } from '@angular/common';
import { ParticipantDto } from '../../core/models/participant.dto';
import { GameService } from '../../core/services/game.service';

@Component({
  selector: 'app-summoner-detailed-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './summoner-detailed-page.html',
  styleUrl: './summoner-detailed-page.css',
})
export class SummonerDetailedPageComponent implements OnInit {
  summoner = signal<ParticipantDto | null>(null);

  constructor(
    private route: ActivatedRoute,
    private gameservice: GameService,
  ) {}

  ngOnInit() {
    const name = this.route.snapshot.paramMap.get('name');
    const tag = this.route.snapshot.paramMap.get('tag');

    if (!name || !tag) return;

    this.gameservice.getLastGamePlayer(name, tag).subscribe((data) => {
      if (!data) return;
      this.summoner.set(data);
    });
  }
}
