export interface FriendGameDetailDto {
  name: string;
  tag: string;
  puuid: string | null;
  champion: string | null;
  championIcon: string | null;
  championSplashArt: string | null;
  kills: number | null;
  deaths: number | null;
  assists: number | null;
  win: boolean | null;
  goldEarned: number | null;
  damageDealt: number | null;
  visionScore: number | null;
  error?: boolean;
}
