package com.lol.backend.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.lol.backend.dto.FriendGameDto;
import com.lol.backend.dto.SummonerGameDetailsDto;
import com.lol.backend.service.FriendService;
import com.lol.backend.service.RiotService;

@RestController
@RequestMapping("/summoner")
@CrossOrigin(origins = "http://localhost:4200")
public class SummonerController {

    private final RiotService riotService;
    private final FriendService friendService;

    public SummonerController(RiotService riotService, FriendService friendService) {
        this.riotService = riotService;
        this.friendService = friendService;
    }

    @GetMapping("/{name}/{tag}")
    public ResponseEntity<?> getSummoner(@PathVariable String name, @PathVariable String tag) {
        return ResponseEntity.ok(riotService.getSummoner(name, tag));
    }

    @GetMapping("/{puuid}/game/{index}")
    public ResponseEntity<SummonerGameDetailsDto> getLastGame(@PathVariable String puuid, @PathVariable int index) {
        return ResponseEntity.ok(riotService.getGame(puuid, index));
    }

    @GetMapping("/friends/games")
    public ResponseEntity<List<FriendGameDto>> getFriendsGames() {
        return ResponseEntity.ok(riotService.getFriendsGame(friendService.getFriends()));
    }
}