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

import static org.hamcrest.Matchers.hasSize;

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

                ParticipantDto yike = new ParticipantDto(
                                "Yike",
                                "puuid-yike-789",
                                "LeeSin",
                                "https://ddragon.leagueoflegends.com/cdn/13.1.1/img/champion/LeeSin.png",
                                "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/LeeSin_0.jpg",
                                5, 4, 12,
                                true,
                                12000, 11000,
                                15000, 22000, 18000,
                                30, 10, 5,
                                180, 4.3, 17,
                                1, 0);

                when(gameService.getLastGamePlayer("Yike", "EUW"))
                                .thenReturn(yike);

                mockMvc.perform(get("/summoner/Yike/EUW/lastgame/player"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.champion").value("LeeSin"));
        }

        @Test
        void shouldReturnLastGameFriends() throws Exception {
                ParticipantDto caps = new ParticipantDto(
                                "Caps",
                                "EUW",
                                "puuid-caps-123",
                                "Ahri",
                                "https://ddragon.leagueoflegends.com/cdn/13.1.1/img/champion/Ahri.png",
                                "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Ahri_0.jpg",
                                10, 2, 8,
                                true,
                                14500, 13000,
                                28000, 35000, 12000,
                                25, 8, 3,
                                210, 9.0, 18,
                                2, 1);

                ParticipantDto hansSama = new ParticipantDto(
                                "Hans Sama",
                                "EUW",
                                "puuid-hans-456",
                                "Jinx",
                                "https://ddragon.leagueoflegends.com/cdn/13.1.1/img/champion/Jinx.png",
                                "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Jinx_0.jpg",
                                14, 3, 6,
                                true,
                                16000, 14500,
                                32000, 40000, 11000,
                                18, 6, 2,
                                250, 6.7, 18,
                                3, 0);

                List<ParticipantDto> friends = List.of(caps, hansSama);

                when(gameService.getLastGameFriends())
                                .thenReturn(friends);

                mockMvc.perform(get("/summoner/friends/lastgame"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].champion").value("Ahri"))
                                .andExpect(jsonPath("$[1].champion").value("Jinx"));
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