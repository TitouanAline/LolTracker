package com.lol.backend.controller;

import com.lol.backend.dto.AccountDto;
import com.lol.backend.dto.GameDto;
import com.lol.backend.dto.ParticipantDto;
import com.lol.backend.service.AccountService;
import com.lol.backend.service.FriendService;
import com.lol.backend.service.GameService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(SummonerController.class)
public class SummonerControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private FriendService friendService;

        @MockitoBean
        private AccountService accountService;

        @MockitoBean
        private GameService gameService;

        @Test
        void shouldReturnAccount() throws Exception {

                AccountDto dto = new AccountDto("Caps", "EUW", "puuid123");

                when(accountService.getAccount("Caps", "EUW"))
                                .thenReturn(dto);

                mockMvc.perform(get("/summoner/Caps/EUW"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Caps"))
                                .andExpect(jsonPath("$.tag").value("EUW"))
                                .andExpect(jsonPath("$.puuid").value("puuid123"));
        }

        @Test
        void shouldReturnLastGamePlayer() throws Exception {

                ParticipantDto dto = new ParticipantDto("Caps",
                                "1234",
                                "Ahri",
                                "",
                                "",
                                0, 0, 0,
                                true, 0, 0,
                                0, 0,
                                0,
                                0, 0, 0,
                                0, 0, 0, 0, 0);

                when(gameService.getLastGamePlayer("Caps", "EUW"))
                                .thenReturn(dto);

                mockMvc.perform(get("/summoner/Caps/EUW/lastgame/player"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.champion").value("Ahri"));
        }

        @Test
        void shouldReturnGameByIndex() throws Exception {

                GameDto dto = new GameDto("MATCH_2", 2000, "ARAM", List.of(), List.of());

                when(gameService.getGame("Caps", "EUW", 2))
                                .thenReturn(dto);

                mockMvc.perform(get("/summoner/Caps/EUW/game/2"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.matchId").value("MATCH_2"));
        }

}