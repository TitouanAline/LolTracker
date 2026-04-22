package com.lol.backend.mapper;

import com.lol.backend.dto.ParticipantDto;

import tools.jackson.databind.JsonNode;

public class ParticipantMapper {

    private static final String VERSION = "14.8.1";

    private ParticipantMapper() {
    }

    public static ParticipantDto toDto(JsonNode p) {

        String champion = p.path("championName").asString();

        String icon = "https://ddragon.leagueoflegends.com/cdn/"
                + VERSION + "/img/champion/" + champion + ".png";

        String splash = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"
                + champion + "_0.jpg";

        int kills = p.path("kills").asInt();
        int deaths = p.path("deaths").asInt();
        int assists = p.path("assists").asInt();

        double kda = (kills + assists) / (double) Math.max(1, deaths);

        int cs = p.path("totalMinionsKilled").asInt()
                + p.path("neutralMinionsKilled").asInt();

        return new ParticipantDto(
                p.path("summonerName").asString(),
                p.path("puuid").asString(),

                champion,
                icon,
                splash,

                kills,
                deaths,
                assists,
                p.path("win").asBoolean(),

                p.path("goldEarned").asInt(),
                p.path("goldSpent").asInt(),

                p.path("totalDamageDealtToChampions").asInt(),
                p.path("totalDamageDealt").asInt(),
                p.path("totalDamageTaken").asInt(),

                p.path("visionScore").asInt(),
                p.path("wardsPlaced").asInt(),
                p.path("wardsKilled").asInt(),

                cs,
                kda,
                p.path("champLevel").asInt(),

                p.path("turretKills").asInt(),
                p.path("inhibitorKills").asInt());
    }
}