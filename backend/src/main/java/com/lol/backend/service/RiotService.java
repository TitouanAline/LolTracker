package com.lol.backend.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lol.backend.dto.LastGameDto;
import com.lol.backend.dto.SummonerDto;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class RiotService {

    private final RestTemplate restTemplate;
    private final Map<String, LastGameDto> cache = new ConcurrentHashMap<>();

    @Value("${riot.api.key}")
    private String apikey;

    public RiotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SummonerDto getSummoner(String name, String tag) {

        String url = "https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + name + "/" + tag;
        HttpEntity<String> entity = buildHeaders();
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        SummonerDto summoner = new SummonerDto(name, tag, response.getBody());
        return summoner;
    }

    public LastGameDto getLastGame(String puuid) {

        try {
            if (cache.containsKey(puuid)) {
                System.out.println("⚡ Cache HIT pour " + puuid);
                return cache.get(puuid);
            }

            String matchIdsUrl = "https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=1";
            HttpEntity<String> entity = buildHeaders();
            ResponseEntity<String> matchIdsResponse = restTemplate.exchange(
                    matchIdsUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            List<String> matchIds = mapper.readValue(matchIdsResponse.getBody(), List.class);

            if (matchIds.isEmpty()) {
                throw new RuntimeException("Aucun match trouvé");
            }

            String matchId = matchIds.get(0);

            String matchDetailUrl = "https://europe.api.riotgames.com/lol/match/v5/matches/" + matchId;

            ResponseEntity<String> matchResponse = restTemplate.exchange(
                    matchDetailUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            JsonNode root = mapper.readTree(matchResponse.getBody());
            JsonNode participants = root.path("info").path("participants");

            for (JsonNode p : participants) {
                if (p.path("puuid").asString().equals(puuid)) {

                    LastGameDto result = new LastGameDto(
                        p.path("championName").asString(),
                        p.path("kills").asInt(),
                        p.path("deaths").asInt(),
                        p.path("assists").asInt(),
                        p.path("win").asBoolean()
                    );

                    cache.put(puuid, result);
                    return result;
                }
            }

            throw new RuntimeException("Joueur non trouvé");

        } catch (Exception e) {
            throw new RuntimeException("Erreur Riot API", e);
        }
    }

    private HttpEntity<String> buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apikey);
        return new HttpEntity<>(headers);
    }
}