export interface ParticipantDto {
  name: string;
  tag: string;
  puuid: string;

  champion: string;
  championIcon: string;
  championSplashArt: string;

  kills: number;
  deaths: number;
  assists: number;
  win: boolean;

  goldEarned: number;
  goldSpent: number;

  damageDealtToChampions: number;
  totalDamageDealt: number;
  damageTaken: number;

  visionScore: number;
  wardsPlaced: number;
  wardsKilled: number;

  cs: number;
  kdaRatio: number;
  level: number;

  turretKills: number;
  inhibitorKills: number;
}
