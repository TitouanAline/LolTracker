package com.lol.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lol.backend.entity.Friend;
import com.lol.backend.repository.FriendRepository;

@Service
public class FriendService {

    private final FriendRepository repository;

    public FriendService(FriendRepository repository) {
        this.repository = repository;
    }

    public List<Friend> getFriends() {
        return repository.findAll();
    }

    public Friend addFriend(Friend friend) {
        return repository.save(friend);
    }
}