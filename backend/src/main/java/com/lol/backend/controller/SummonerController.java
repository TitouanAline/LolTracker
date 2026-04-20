package com.lol.backend.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/summoner")
@CrossOrigin(origins = "http://localhost:4200")
public class SummonerController {

    private final RestTemplate restTemplate;
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    
    @Value("${riot.api.key}")
    private String apiKey;

    public SummonerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{name}/{tag}")
    public ResponseEntity<String> getSummoner(@PathVariable String name, @PathVariable String tag) {

        String url = "https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + name + "/" + tag;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response;
    }
    @GetMapping("/last-game/{puuid}")
    public ResponseEntity<?> getLastGameChampion(@PathVariable String puuid) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Riot-Token", apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String cacheKey = puuid;

            if (cache.containsKey(cacheKey)) {
                System.out.println("⚡ Cache HIT pour " + puuid);
                return ResponseEntity.ok(cache.get(cacheKey));
            }

            // 1. récupérer les match IDs
            String matchIdsUrl = "https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                    + puuid + "/ids?start=0&count=1";

            ResponseEntity<String> matchIdsResponse = restTemplate.exchange(
                    matchIdsUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            List<String> matchIds = mapper.readValue(matchIdsResponse.getBody(), List.class);

            if (matchIds.isEmpty()) {
                return ResponseEntity.ok("Aucun match trouvé");
            }

            String matchId = matchIds.get(0);

            // 2. récupérer le détail du match
            String matchDetailUrl = "https://europe.api.riotgames.com/lol/match/v5/matches/" + matchId;

            ResponseEntity<String> matchResponse = restTemplate.exchange(
                    matchDetailUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            JsonNode root = mapper.readTree(matchResponse.getBody());
            JsonNode participants = root.path("info").path("participants");

            // 3. trouver ton joueur
            for (JsonNode p : participants) {

                if (p.path("puuid").asString().equals(puuid)) {

                    Map<String, Object> result = Map.of(
                        "champion", p.path("championName").asString(),
                        "kills", p.path("kills").asInt(),
                        "deaths", p.path("deaths").asInt(),
                        "assists", p.path("assists").asInt(),
                        "win", p.path("win").asBoolean()
                    );

                    // 🔥 SAVE CACHE
                    cache.put(puuid, result);

                    return ResponseEntity.ok(result);
                }
            }

            return ResponseEntity.status(404).body("Joueur non trouvé dans le match");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur: " + e.getMessage());
        }
    }
}