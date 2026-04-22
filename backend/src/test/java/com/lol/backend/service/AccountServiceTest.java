package com.lol.backend.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.lol.backend.dto.AccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private RestTemplate restTemplate;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        accountService = new AccountService(restTemplate, "test-key");
    }

    @Test
    void shouldReturnAccountDto_whenApiReturnsValidResponse() {
        String fakeJson = """
                    {
                      "puuid": "12345",
                      "gameName": "Caps",
                      "tagLine": "EUW"
                    }
                """;

        ResponseEntity<String> response = new ResponseEntity<>(fakeJson, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class))).thenReturn(response);

        AccountDto result = accountService.getSummoner("Caps", "EUW");

        assertNotNull(result);
        assertEquals("Caps", result.getName());
        assertEquals("EUW", result.getTag());
        assertEquals("12345", result.getPuuid());
    }
}