package com.lol.backend.service;

import com.lol.backend.dto.AccountDto;
import com.lol.backend.dto.GameDto;
import com.lol.backend.dto.ParticipantDto;
import com.lol.backend.mapper.GameMapper;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GameService {

    private final RestTemplate restTemplate;
    private final AccountService accountService;
    private final FriendService friendService;
    private final String apiKey;

    public GameService(
            RestTemplate restTemplate,
            AccountService accountService,
            FriendService friendService,
            @Value("${riot.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.accountService = accountService;
        this.friendService = friendService;
        this.apiKey = apiKey;
    }

    public ParticipantDto getLastGamePlayer(String name, String tag) {

        AccountDto account = accountService.getAccount(name, tag);

        String matchId = getMatchId(account.getPuuid(), 0);

        JsonNode matchDetails = getMatchDetails(matchId);

        GameDto game = GameMapper.toDto(matchDetails);

        ParticipantDto player = findPlayer(game, account.getPuuid());

        return fillWithPlayerNameAndTag(account, player);
    }

    public List<ParticipantDto> getLastGameFriends() {
        List<ParticipantDto> res = friendService.getFriends().stream()
                .map(f -> getLastGamePlayer(f.getName(), f.getTag()))
                .toList();

        return res;
    }

    public GameDto getGame(String name, String tag, int index) {

        AccountDto account = accountService.getAccount(name, tag);

        String matchId = getMatchId(account.getPuuid(), index);

        JsonNode matchDetails = getMatchDetails(matchId);

        return GameMapper.toDto(matchDetails);
    }

    private String getMatchId(String puuid, int index) {

        String url = "https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/"
                + puuid + "/ids?start=" + index + "&count=1";

        String body = get(url);

        ObjectMapper mapper = new ObjectMapper();

        try {
            String[] matchIds = mapper.readValue(body, String[].class);
            return matchIds[0];

        } catch (Exception e) {
            throw new RuntimeException("Erreur récupération matchId", e);
        }
    }

    private JsonNode getMatchDetails(String matchId) {

        String url = "https://europe.api.riotgames.com/lol/match/v5/matches/" + matchId;

        String body = get(url);

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readTree(body);
        } catch (Exception e) {
            throw new RuntimeException("Erreur parsing matchDetails", e);
        }
    }

    private String get(String url) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }

    private ParticipantDto findPlayer(GameDto game, String puuid) {

        return game.getTeam1().stream()
                .filter(p -> p.getPuuid().equals(puuid))
                .findFirst()
                .orElseGet(() -> game.getTeam2().stream()
                        .filter(p -> p.getPuuid().equals(puuid))
                        .findFirst()
                        .orElseThrow());
    }

    private ParticipantDto fillWithPlayerNameAndTag(AccountDto account, ParticipantDto player) {
        return new ParticipantDto(
                account.getName(),
                account.getTag(),
                player.getPuuid(),

                player.getChampion(),
                player.getChampionIcon(),
                player.getChampionSplashArt(),

                player.getKills(),
                player.getDeaths(),
                player.getAssists(),
                player.isWin(),

                player.getGoldEarned(),
                player.getGoldSpent(),

                player.getDamageDealtToChampions(),
                player.getTotalDamageDealt(),
                player.getDamageTaken(),

                player.getVisionScore(),
                player.getWardsPlaced(),
                player.getWardsKilled(),

                player.getCs(),
                player.getKdaRatio(),
                player.getLevel(),

                player.getTurretKills(),
                player.getInhibitorKills());
    }
}