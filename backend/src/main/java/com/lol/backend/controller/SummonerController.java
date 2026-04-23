package com.lol.backend.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.lol.backend.dto.AccountDto;
import com.lol.backend.dto.GameDto;
import com.lol.backend.dto.GamePreviewDto;
import com.lol.backend.dto.ParticipantDto;
import com.lol.backend.service.AccountService;
import com.lol.backend.service.FriendService;
import com.lol.backend.service.GameService;

@RestController
@RequestMapping("/summoner")
@CrossOrigin(origins = "http://localhost:4200")
public class SummonerController {

    private final FriendService friendService;
    private final AccountService accountService;
    private final GameService gameService;

    public SummonerController(
            FriendService friendService,
            AccountService accountService,
            GameService gameService) {
        this.friendService = friendService;
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

    @GetMapping("/{name}/{tag}/game/{index}")
    public ResponseEntity<GameDto> getGame(@PathVariable String name, @PathVariable String tag,
            @PathVariable int index) {
        return ResponseEntity.ok(gameService.getGame(name, tag, index));
    }

    @GetMapping("/friends/games")
    public ResponseEntity<List<GamePreviewDto>> getFriendsGames() {

        List<GamePreviewDto> games = friendService.getFriends().stream()
                .map(f -> gameService.getLastGamePreview(f.getName(), f.getTag()))
                .toList();

        return ResponseEntity.ok(games);
    }
}