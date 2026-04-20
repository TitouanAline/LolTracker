package com.lol.backend.controller;

import com.lol.backend.dto.LastGameDto;
import com.lol.backend.dto.SummonerDto;
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

    @BeforeEach
    void setup() {
        riotService = Mockito.mock(RiotService.class);

        SummonerController controller = new SummonerController(riotService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnLastGame() throws Exception {

        LastGameDto dto = new LastGameDto("Ahri", 10, 2, 5, true);

        when(riotService.getLastGame("test-puuid")).thenReturn(dto);

        mockMvc.perform(get("/summoner/last-game/test-puuid").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.champion").value("Ahri"))
                        .andExpect(jsonPath("$.kills").value(10))
                        .andExpect(jsonPath("$.deaths").value(2))
                        .andExpect(jsonPath("$.assists").value(5))
                        .andExpect(jsonPath("$.win").value(true));
    }
    @Test
    void shouldReturnSummoner() throws Exception {

        SummonerDto dto = new SummonerDto("test-summoner", "test-tag", "test-puuid");

        when(riotService.getSummoner("test-summoner", "test-tag")).thenReturn(dto);

        mockMvc.perform(get("/summoner/test-summoner/test-tag").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.name").value("test-summoner"))
                        .andExpect(jsonPath("$.tag").value("test-tag"))
                        .andExpect(jsonPath("$.puuid").value("test-puuid"));
    }
}