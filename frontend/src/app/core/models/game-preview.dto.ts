export interface GamePreviewDto {
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
  damage: number;
  visionScore: number;

  kda: number;
}
