import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ParticipantDto } from '../../../core/models/participant.dto';

import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-game-preview',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game-preview.html',
  styleUrl: './game-preview.css',
})
export class GamePreviewComponent {
  @Input() data!: ParticipantDto;
  @Output() clicked = new EventEmitter<ParticipantDto>();

  onClick() {
    this.clicked.emit(this.data);
  }
}
