package com.lol.backend.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import com.lol.backend.dto.ParticipantDto;
import com.lol.backend.model.Friend;

import static org.assertj.core.api.Assertions.assertThat;

class GameServiceTest {

        @Mock
        private RestTemplate restTemplate;

        @Mock
        private AccountService accountService;

        @Mock
        private FriendService friendService;

        @InjectMocks
        private GameService gameService;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                gameService = new GameService(restTemplate, accountService, friendService, "API_KEY");
        }

        @Test
        void shouldReturnFriendsGames() {

                List<Friend> friends = List.of(
                                new Friend("Caps", "EUW"),
                                new Friend("Yike", "EUW"));

                when(friendService.getFriends()).thenReturn(friends);

                GameService spyService = spy(gameService);

                ParticipantDto caps = mock(ParticipantDto.class);
                ParticipantDto yike = mock(ParticipantDto.class);

                doReturn(caps).when(spyService).getLastGamePlayer("Caps", "EUW");
                doReturn(yike).when(spyService).getLastGamePlayer("Yike", "EUW");

                List<ParticipantDto> result = spyService.getLastGameFriends();

                assertThat(result).hasSize(2);
                assertThat(result.get(0)).isEqualTo(caps);
                assertThat(result.get(1)).isEqualTo(yike);
        }

}