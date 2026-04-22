package com.lol.backend.mapper;

import com.lol.backend.dto.GameDto;
import com.lol.backend.dto.ParticipantDto;

import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class GameMapper {

    private static int TEAM_BLUE_ID = 100;
    private static int TEAM_RED_ID = 200;

    private GameMapper() {
    }

    public static GameDto toDto(JsonNode matchDetails) {

        JsonNode info = matchDetails.path("info");
        JsonNode metadata = matchDetails.path("metadata");
        JsonNode participants = info.path("participants");

        List<ParticipantDto> team1 = new ArrayList<>();
        List<ParticipantDto> team2 = new ArrayList<>();

        for (JsonNode p : participants) {

            ParticipantDto dto = ParticipantMapper.toDto(p);

            int teamId = p.path("teamId").asInt();

            if (teamId == TEAM_BLUE_ID) {
                team1.add(dto);
            } else if (teamId == TEAM_RED_ID) {
                team2.add(dto);
            }
        }

        return new GameDto(
                metadata.path("matchId").asString(),
                info.path("gameDuration").asInt(),
                info.path("gameMode").asString(),
                team1,
                team2);
    }
}