package com.lol.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lol.backend.dto.AccountDto;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class AccountService {

    private final RestTemplate restTemplate;
    private final String apiKey;

    public AccountService(RestTemplate restTemplate, @Value("${riot.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public AccountDto getSummoner(String name, String tag) {

        String url = "https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + name + "/" + tag;

        String body = get(url);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(body);

            return new AccountDto(
                    json.get("gameName").asString(),
                    json.get("tagLine").asString(),
                    json.get("puuid").asString());

        } catch (Exception e) {
            throw new RuntimeException("Erreur parsing Riot API", e);
        }
    }

    private HttpEntity<String> buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);
        return new HttpEntity<>(headers);
    }

    private String get(String url) {
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                buildHeaders(),
                String.class);
        return response.getBody();
    }

}
