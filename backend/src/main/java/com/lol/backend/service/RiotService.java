package com.lol.backend.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lol.backend.dto.AccountDto;
import com.lol.backend.dto.FriendGameDto;
import com.lol.backend.dto.SummonerGameDetailsDto;
import com.lol.backend.mapper.FriendMapper;
import com.lol.backend.mapper.SummonerDetailsMapper;
import com.lol.backend.model.Friend;
import com.lol.backend.util.CacheEntry;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class RiotService {

    private final RestTemplate restTemplate;
    private final Map<String, CacheEntry<SummonerGameDetailsDto>> cache = new ConcurrentHashMap<>();

    @Value("${riot.api.key}")
    private String apikey;

    public RiotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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

    public SummonerGameDetailsDto getGame(String puuid, int gameIndexDesc) {

        if (isValidValueInCache(puuid)) {
            return cache.get(puuid).getValue();
        }

        String matchId = getMatchId(puuid, gameIndexDesc);
        JsonNode matchDetails = getMatchDetails(matchId);
        SummonerGameDetailsDto result = extractPlayerStats(matchDetails, puuid);

        Instant expiresAt = Instant.now().plusSeconds(600);
        cache.put(puuid, new CacheEntry<SummonerGameDetailsDto>(result, expiresAt));

        return result;
    }

    public List<FriendGameDto> getFriendsGame(List<Friend> friends) {
        return friends.stream()
                .map(this::getFriendGame)
                .toList();
    }

    private FriendGameDto getFriendGame(Friend friend) {
        try {
            AccountDto account = getSummoner(friend.getName(), friend.getTag());
            SummonerGameDetailsDto game = getGame(account.getPuuid(), 0);

            return FriendMapper.mapSuccess(account, game);

        } catch (Exception e) {
            return FriendMapper.mapError(friend);
        }
    }

    private String getMatchId(String puuid, int gameIndexDesc) {

        try {
            String url = "https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start="
                    + gameIndexDesc + "&count=1";

            String body = get(url);

            ObjectMapper mapper = new ObjectMapper();

            List<String> matchIds = mapper.readValue(
                    body,
                    mapper.getTypeFactory().constructCollectionType(List.class, String.class));

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

                return SummonerDetailsMapper.toDto(
                        p.path("championName").asString(),
                        p.path("kills").asInt(),
                        p.path("deaths").asInt(),
                        p.path("assists").asInt(),
                        p.path("win").asBoolean(),
                        p.path("goldEarned").asInt(),
                        p.path("totalDamageDealtToChampions").asInt(),
                        p.path("visionScore").asInt());
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
                String.class);
        return response.getBody();
    }

    private Boolean isValidValueInCache(String id) {
        return cache.containsKey(id) && !cache.get(id).isExpired();
    }
}