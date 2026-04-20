package com.lol.backend.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lol.backend.dto.SummonerDto;
import com.lol.backend.dto.SummonerGameDetailsDto;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class RiotService {

    private final RestTemplate restTemplate;
    private final Map<String, SummonerGameDetailsDto> cache = new ConcurrentHashMap<>();

    @Value("${riot.api.key}")
    private String apikey;

    public RiotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SummonerDto getSummoner(String name, String tag) {

        String url = "https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + name + "/" + tag;

        String body = get(url);

        SummonerDto summoner = new SummonerDto(name, tag, body);
        return summoner;
    }

    public SummonerGameDetailsDto getGame(String puuid, int gameIndexDesc) {

        if (cache.containsKey(puuid)) {
            System.out.println("⚡ Cache HIT pour " + puuid);
            return cache.get(puuid);
        }

        String matchId = getMatchId(puuid, gameIndexDesc);
        JsonNode matchDetails = getMatchDetails(matchId);
        SummonerGameDetailsDto result = extractPlayerStats(matchDetails, puuid);

        cache.put(puuid, result);

        return result;
    }

    private String getMatchId(String puuid, int gameIndexDesc) {

        try {
            String url = "https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=" + gameIndexDesc + "&count=1";

            String body = get(url);

            ObjectMapper mapper = new ObjectMapper();

            List<String> matchIds = mapper.readValue(
                    body,
                    mapper.getTypeFactory().constructCollectionType(List.class, String.class)
            );

            if (matchIds.isEmpty()) {
                throw new RuntimeException("Aucun match trouvé");
            }

            return matchIds.get(0);

        } catch (Exception e) {
            throw new RuntimeException("Erreur récupération matchId", e);
        }
    }
    private JsonNode getMatchDetails(String matchId) {

        try {
            String url = "https://europe.api.riotgames.com/lol/match/v5/matches/" + matchId;

            String body = get(url);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(body);

        } catch (Exception e) {
            throw new RuntimeException("Erreur récupération match details", e);
        }
    }
    private SummonerGameDetailsDto extractPlayerStats(JsonNode matchDetails, String puuid) {

        JsonNode participants = matchDetails.path("info").path("participants");

        for (JsonNode p : participants) {
            if (p.path("puuid").asString().equals(puuid)) {

                return new SummonerGameDetailsDto(
                        p.path("championName").asString(),
                        p.path("kills").asInt(),
                        p.path("deaths").asInt(),
                        p.path("assists").asInt(),
                        p.path("win").asBoolean()
                );
            }
        }

        throw new RuntimeException("Joueur non trouvé dans le match");
    }

    private HttpEntity<String> buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apikey);
        return new HttpEntity<>(headers);
    }

    private String get(String url) {
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                buildHeaders(),
                String.class
        );
        return response.getBody();
    }
}