import { PlayerGameDto } from './player-game.dto';

export interface GameDto {
  matchId: string;

  // ⏱️ infos globales
  gameDuration: number;
  gameMode: string;

  team1: PlayerGameDto[];
  team2: PlayerGameDto[];
}
