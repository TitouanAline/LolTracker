package com.lol.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDto {
    private String name;
    private String tag;
    private String puuid;
}