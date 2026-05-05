package com.lol.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lol.backend.entity.UserFriend;

public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    List<UserFriend> findByUserId(Long userId);

    boolean existsByUserIdAndFriendId(Long userId, Long friendId);

    UserFriend findByUserIdAndFriendId(Long userId, Long friendId);

}
