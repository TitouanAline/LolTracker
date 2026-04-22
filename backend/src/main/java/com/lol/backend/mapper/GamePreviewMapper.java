package com.lol.backend.mapper;

import com.lol.backend.dto.GamePreviewDto;
import com.lol.backend.dto.ParticipantDto;

public class GamePreviewMapper {

    private GamePreviewMapper() {
    }

    public static GamePreviewDto from(String name, String tag, String puuid, ParticipantDto p) {

        return new GamePreviewDto(
                name,
                tag,
                puuid,

                p.getChampion(),
                p.getChampionIcon(),
                p.getChampionSplashArt(),

                p.getKills(),
                p.getDeaths(),
                p.getAssists(),
                p.isWin(),

                p.getGoldEarned(),
                p.getDamageDealtToChampions(),
                p.getVisionScore(),

                p.getKdaRatio());
    }

    public static GamePreviewDto error(String name, String tag) {
        return new GamePreviewDto(
                name,
                tag,
                null,
                null, null, null,
                0, 0, 0, false,
                0, 0, 0,
                0);
    }
}