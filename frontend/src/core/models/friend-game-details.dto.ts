export interface FriendGameDetailDto {
  name: string;
  tag: string;
  champion: string | null;
  championImage: string | null;
  splashImage: string | null;
  kills: number | null;
  deaths: number | null;
  assists: number | null;
  win: boolean | null;
  error?: boolean;
}
