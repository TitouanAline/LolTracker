package com.lol.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LastGameDto {
    private String champion;
    private int kills;
    private int deaths;
    private int assists;
    private boolean win;
}