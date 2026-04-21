export interface FriendGameDetailDto {
  name: string;
  tag: string;
  champion: string | null;
  championIcon: string | null;
  championSplashArt: string | null;
  kills: number | null;
  deaths: number | null;
  assists: number | null;
  win: boolean | null;
  error?: boolean;
}
