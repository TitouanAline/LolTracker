package com.lol.backend.service;

import com.lol.backend.dto.SummonerGameDetailsDto;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class RiotServiceTest {

    @Test
    void shouldReturnLastGameDto() throws Exception {

        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        String matchIdString = "EUW1_123456";

        String matchIdsJson = "[\""+matchIdString+"\"]";
        String matchDetailJson = """
        {
          "info": {
            "participants": [
              {
                "puuid": "test-puuid",
                "championName": "Ahri",
                "kills": 10,
                "deaths": 2,
                "assists": 5,
                "win": true
              }
            ]
          }
        }
        """;

        when(restTemplate.exchange(
                contains("/by-puuid"),
                eq(HttpMethod.GET),
                any(),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(matchIdsJson, HttpStatus.OK));

        when(restTemplate.exchange(
                contains("/matches/" + matchIdString),
                eq(HttpMethod.GET),
                any(),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(matchDetailJson, HttpStatus.OK));

        // 4. Service avec mock
        RiotService service = new RiotService(restTemplate);

        // 5. Appel méthode
        SummonerGameDetailsDto result = service.getGame("test-puuid", 0);

        // 6. Vérifications
        assertNotNull(result);
        assertEquals("Ahri", result.getChampion());
        assertEquals(10, result.getKills());
        assertEquals(2, result.getDeaths());
        assertEquals(5, result.getAssists());
        assertTrue(result.isWin());
    }
}