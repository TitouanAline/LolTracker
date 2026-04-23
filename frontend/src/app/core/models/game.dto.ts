import { ParticipantDto } from './participant.dto';

export interface GameDto {
  matchId: string;

  gameDuration: number;
  gameMode: string;

  team1: ParticipantDto[];
  team2: ParticipantDto[];
}
