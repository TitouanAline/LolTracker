package com.lol.backend.controller;

import com.lol.backend.dto.AccountDto;
import com.lol.backend.dto.SummonerGameDetailsDto;
import com.lol.backend.service.FriendService;
import com.lol.backend.service.RiotService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SummonerControllerTest {

    private MockMvc mockMvc;
    private RiotService riotService;
    private FriendService friendService;

    @BeforeEach
    void setup() {
        riotService = Mockito.mock(RiotService.class);
        friendService = Mockito.mock(FriendService.class);

        SummonerController controller = new SummonerController(riotService, friendService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnLastGame() throws Exception {

        SummonerGameDetailsDto dto = new SummonerGameDetailsDto("Ahri", "Null", "Null", 10, 2, 5, true, 2000, 1000, 12);

        when(riotService.getGame("test-puuid", 0)).thenReturn(dto);

        mockMvc.perform(get("/summoner/test-puuid/game/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.champion").value("Ahri"))
                .andExpect(jsonPath("$.kills").value(10))
                .andExpect(jsonPath("$.deaths").value(2))
                .andExpect(jsonPath("$.assists").value(5))
                .andExpect(jsonPath("$.win").value(true))
                .andExpect(jsonPath("$.goldEarned").value(2000))
                .andExpect(jsonPath("$.damageDealt").value(1000))
                .andExpect(jsonPath("$.visionScore").value(12));
    }

    @Test
    void shouldReturnSummoner() throws Exception {

        AccountDto dto = new AccountDto("test-summoner", "test-tag", "test-puuid");

        when(riotService.getSummoner("test-summoner", "test-tag")).thenReturn(dto);

        mockMvc.perform(get("/summoner/test-summoner/test-tag").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("test-summoner"))
                .andExpect(jsonPath("$.tag").value("test-tag"))
                .andExpect(jsonPath("$.puuid").value("test-puuid"));
    }
}