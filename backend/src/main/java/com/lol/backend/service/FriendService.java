package com.lol.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lol.backend.model.Friend;

@Service
public class FriendService {

    private final List<Friend> friends = List.of(
            new Friend("Fyralll", "5403"),
            new Friend("G2 SkewMond", "3327"),
            new Friend("G2 Caps", "1323"),
            new Friend("Nathanzor", "EUW"));

    public List<Friend> getFriends() {
        return friends;
    }
}