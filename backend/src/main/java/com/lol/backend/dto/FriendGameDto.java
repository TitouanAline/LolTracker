package com.lol.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendGameDto {

    private String name;
    private String tag;
    private String puuid;

    private String champion;
    private String championIcon;
    private String championSplashArt;

    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Boolean win;

    private Integer goldEarned;
    private Integer damageDealt;
    private Integer visionScore;

    private boolean error;
}