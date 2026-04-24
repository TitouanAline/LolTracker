package com.lol.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.backend.entity.Friend;
import com.lol.backend.service.FriendService;

@RestController
@RequestMapping("/friends")
public class FriendController {

    private final FriendService service;

    public FriendController(FriendService service) {
        this.service = service;
    }

    @GetMapping
    public List<Friend> getAll() {
        return service.getFriends();
    }

    @PostMapping
    public Friend add(@RequestBody Friend friend) {
        return service.addFriend(friend);
    }

    @GetMapping("/doAlreadyExists/{name}/{tag}")
    public Boolean alreadyExists(@PathVariable String name, @PathVariable String tag) {
        return service.alreadyExists(new Friend(name, tag));
    }
}