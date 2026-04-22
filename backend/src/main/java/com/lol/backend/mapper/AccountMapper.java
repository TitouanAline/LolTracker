package com.lol.backend.mapper;

import com.lol.backend.dto.AccountDto;

import tools.jackson.databind.JsonNode;

public class AccountMapper {

    public static AccountDto toDto(JsonNode json) {
        if (json == null || json.isEmpty()) {
            throw new RuntimeException("Account JSON vide");
        }

        return new AccountDto(
                json.path("gameName").asString(),
                json.path("tagLine").asString(),
                json.path("puuid").asString());
    }
}