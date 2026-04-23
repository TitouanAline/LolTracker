package com.lol.backend.service;

import com.lol.backend.dto.AccountDto;
import com.lol.backend.dto.GamePreviewDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GameServiceTest {

        @Mock
        private RestTemplate restTemplate;

        @Mock
        private AccountService accountService;

        @Mock
        private FriendService friendService;

        @InjectMocks
        private GameService gameService;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                gameService = new GameService(restTemplate, accountService, friendService, "API_KEY");
        }

        @Test
        void shouldReturnGamePreview() throws Exception {
                String puuid = "puuid123";

                when(accountService.getAccount("Caps", "EUW"))
                                .thenReturn(new AccountDto("Caps", "EUW", puuid));

                ResponseEntity<String> matchIdResponse = new ResponseEntity<>(
                                "[\"MATCH_1\"]",
                                HttpStatus.OK);

                String matchDetailsJson = """
                                {
                                  "metadata": { "matchId": "MATCH_1" },
                                  "info": {
                                    "gameDuration": 1800,
                                    "gameMode": "CLASSIC",
                                    "participants": [
                                      {
                                        "puuid": "puuid123",
                                        "summonerName": "Caps",
                                        "championName": "Ahri",
                                        "kills": 10,
                                        "deaths": 2,
                                        "assists": 5,
                                        "win": true,
                                        "goldEarned": 12000,
                                        "goldSpent": 11000,
                                        "totalDamageDealtToChampions": 20000,
                                        "totalDamageDealt": 50000,
                                        "totalDamageTaken": 15000,
                                        "visionScore": 20,
                                        "wardsPlaced": 5,
                                        "wardsKilled": 2,
                                        "totalMinionsKilled": 150,
                                        "neutralMinionsKilled": 10,
                                        "champLevel": 16,
                                        "turretKills": 2,
                                        "inhibitorKills": 1,
                                        "teamId": 100
                                      }
                                    ]
                                  }
                                }
                                """;

                ResponseEntity<String> matchDetailsResponse = new ResponseEntity<>(
                                matchDetailsJson,
                                HttpStatus.OK);

                when(restTemplate.exchange(
                                contains("ids"),
                                eq(HttpMethod.GET),
                                any(),
                                eq(String.class)))
                                .thenReturn(matchIdResponse);

                when(restTemplate.exchange(
                                contains("matches/MATCH_1"),
                                eq(HttpMethod.GET),
                                any(),
                                eq(String.class)))
                                .thenReturn(matchDetailsResponse);

                GamePreviewDto result = gameService.getLastGamePreview("Caps", "EUW");

                assertNotNull(result);
                assertEquals("Ahri", result.getChampion());
                assertEquals(10, result.getKills());
                assertTrue(result.isWin());
        }

        @Test
        void shouldReturnErrorPreviewWhenApiFails() {

                when(accountService.getAccount(any(), any()))
                                .thenThrow(new RuntimeException());

                GamePreviewDto result = gameService.getLastGamePreview("Test", "EUW");

                assertNotNull(result);
                assertNull(result.getChampion());
        }
}