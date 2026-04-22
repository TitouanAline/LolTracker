package com.lol.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GamePreviewDto {

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
    private int damage;
    private int visionScore;

    private double kda;
}