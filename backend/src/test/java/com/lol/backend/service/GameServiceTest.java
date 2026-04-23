package com.lol.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

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

}