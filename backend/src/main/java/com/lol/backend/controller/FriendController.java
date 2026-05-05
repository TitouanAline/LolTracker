package com.lol.backend.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.backend.entity.Friend;
import com.lol.backend.entity.User;
import com.lol.backend.entity.UserFriend;
import com.lol.backend.service.FriendService;
import com.lol.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    private User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userService.findByEmail(email);
    }

    @GetMapping
    public List<Friend> getAll() {
        return friendService.getFollowedFriends(getCurrentUser());
    }

    @PostMapping("/add")
    public UserFriend add(@RequestBody Friend friend) {

        System.out.println(friend.getName());
        System.out.println(friend.getTag());

        return friendService.followFriend(getCurrentUser(), friend);
    }

    @GetMapping("/doAlreadyExists/{name}/{tag}")
    public Boolean alreadyExists(@PathVariable String name, @PathVariable String tag) {
        User user = getCurrentUser();
        return friendService.isAlreadyFollowed(user, name, tag);
    }

    @DeleteMapping("/remove/{name}/{tag}")
    public void removeFriend(
            @PathVariable String name,
            @PathVariable String tag) {
        friendService.unfollow(getCurrentUser(), name, tag);
    }
}