package com.lol.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParticipantDto {
    private String name;
    private String tag;
    private String puuid;

    private String champion;
    private String championIcon;
    private String championSplashArt;

    private int kills;
    private int deaths;
    private int assists;
    private boolean win;

    private int goldEarned;
    private int goldSpent;

    private int damageDealtToChampions;
    private int totalDamageDealt;
    private int damageTaken;

    private int visionScore;
    private int wardsPlaced;
    private int wardsKilled;

    private int cs;
    private double kdaRatio;
    private int level;

    private int turretKills;
    private int inhibitorKills;
}