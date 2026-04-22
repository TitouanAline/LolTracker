package com.lol.backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameDto {
    private String matchId;

    private int gameDuration;
    private String gameMode;

    private List<ParticipantDto> team1;
    private List<ParticipantDto> team2;
}
