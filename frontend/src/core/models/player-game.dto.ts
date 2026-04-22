export interface PlayerGameDto {
  // 👤 joueur
  name: string;
  tag: string;
  puuid: string;

  // 🧙 champion
  champion: string;
  championIcon: string;
  championSplashArt: string;

  // ⚔️ combat
  kills: number;
  deaths: number;
  assists: number;
  win: boolean;

  // 💰 économie
  goldEarned: number;
  goldSpent: number;

  // ⚔️ dégâts
  damageDealtToChampions: number;
  totalDamageDealt: number;
  damageTaken: number;

  // 👁️ vision
  visionScore: number;
  wardsPlaced: number;
  wardsKilled: number;

  // 🎮 gameplay
  cs: number;
  kdaRatio: number;
  level: number;

  // 🏆 objectifs
  turretKills: number;
  inhibitorKills: number;
}
