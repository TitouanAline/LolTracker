package com.lol.backend.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.lol.backend.dto.AccountDto;
import com.lol.backend.dto.GameDto;
import com.lol.backend.dto.ParticipantDto;
import com.lol.backend.service.AccountService;
import com.lol.backend.service.GameService;

@RestController
@RequestMapping("/summoner")
@CrossOrigin(origins = "http://localhost:4200")
public class SummonerController {

    private final AccountService accountService;
    private final GameService gameService;

    public SummonerController(
            AccountService accountService,
            GameService gameService) {
        this.accountService = accountService;
        this.gameService = gameService;
    }

    @GetMapping("/{name}/{tag}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable String name, @PathVariable String tag) {
        return ResponseEntity.ok(accountService.getAccount(name, tag));
    }

    @GetMapping("/{name}/{tag}/lastgame/player")
    public ResponseEntity<ParticipantDto> getLastGamePlayer(@PathVariable String name, @PathVariable String tag) {
        return ResponseEntity.ok(gameService.getLastGamePlayer(name, tag));
    }

    @GetMapping("/friends/lastgame")
    public ResponseEntity<List<ParticipantDto>> getLastGameFriends() {
        return ResponseEntity.ok(gameService.getLastGameFriends());
    }

    @GetMapping("/{name}/{tag}/game/{index}")
    public ResponseEntity<GameDto> getGame(@PathVariable String name, @PathVariable String tag,
            @PathVariable int index) {
        return ResponseEntity.ok(gameService.getGame(name, tag, index));
    }
}