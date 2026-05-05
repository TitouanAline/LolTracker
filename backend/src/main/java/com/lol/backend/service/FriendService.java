package com.lol.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lol.backend.entity.Friend;
import com.lol.backend.entity.User;
import com.lol.backend.entity.UserFriend;
import com.lol.backend.repository.FriendRepository;
import com.lol.backend.repository.UserFriendRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserFriendRepository userFriendRepository;
    private final FriendRepository repository;

    public List<Friend> getFollowedFriends(User user) {
        return userFriendRepository.findByUserId(user.getId()).stream()
                .map(UserFriend::getFriend)
                .collect(Collectors.toList());
    }

    public UserFriend followFriend(User user, Friend friendRequest) {

        Friend friend = repository
                .findByNameAndTag(friendRequest.getName(), friendRequest.getTag());

        if (friend == null) {
            friend = repository.save(friendRequest);
        }

        if (isAlreadyFollowed(user, friendRequest.getName(), friendRequest.getTag())) {
            throw new RuntimeException("Already followed");
        }

        UserFriend relation = new UserFriend();
        relation.setUser(user);
        relation.setFriend(friend);

        return userFriendRepository.save(relation);
    }

    public boolean isAlreadyFollowed(User user, String name, String tag) {
        Friend friend = repository.findByNameAndTag(name, tag);
        if (friend == null) {
            return false;
        }
        return userFriendRepository.existsByUserIdAndFriendId(
                user.getId(),
                friend.getId());
    }

    @Transactional
    public void unfollow(User user, String name, String tag) {

        Friend friend = repository.findByNameAndTag(name, tag);

        if (friend == null) {
            System.out.println("Friend not found");
            return;
        }

        UserFriend relation = userFriendRepository.findByUserIdAndFriendId(user.getId(), friend.getId());

        if (relation == null) {
            System.out.println("Relation not found");
            return;
        }

        userFriendRepository.delete(relation);
    }
}