package com.lol.backend.mapper;

import com.lol.backend.dto.AccountDto;
import com.lol.backend.dto.FriendGameDto;
import com.lol.backend.dto.SummonerGameDetailsDto;
import com.lol.backend.model.Friend;

public class FriendMapper {

    private static final String VERSION = "16.8.1";

    public static FriendGameDto mapError(Friend friend) {
        return new FriendGameDto(
                friend.getName(),
                friend.getTag(),
                null,
                null, null, null,
                null, null, null,
                null,
                null, null, null, true);
    }

    public static FriendGameDto mapSuccess(AccountDto friend, SummonerGameDetailsDto game) {
        String champion = game.getChampion();

        String icon = "https://ddragon.leagueoflegends.com/cdn/"
                + VERSION + "/img/champion/" + champion + ".png";

        String splash = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"
                + champion + "_0.jpg";

        return new FriendGameDto(
                friend.getName(),
                friend.getTag(),
                friend.getPuuid(),
                champion,
                icon,
                splash,
                game.getKills(),
                game.getDeaths(),
                game.getAssists(),
                game.isWin(),
                game.getGoldEarned(),
                game.getDamageDealt(),
                game.getVisionScore(),
                false);
    }
}
