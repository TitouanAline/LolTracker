package com.lol.backend.mapper;

import com.lol.backend.dto.SummonerGameDetailsDto;

public class SummonerMapper {

        private static final String VERSION = "16.8.1";

        public static SummonerGameDetailsDto toDto(
                        String champion,
                        int kills,
                        int deaths,
                        int assists,
                        boolean win,
                        int gold,
                        int damage,
                        int vision) {

                String icon = "https://ddragon.leagueoflegends.com/cdn/"
                                + VERSION + "/img/champion/" + champion + ".png";

                String splash = "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/"
                                + champion + "_0.jpg";

                SummonerGameDetailsDto result = new SummonerGameDetailsDto(
                                champion,
                                icon,
                                splash,
                                kills,
                                deaths,
                                assists,
                                win,
                                gold,
                                damage,
                                vision);
                return result;
        }
}