package com.lol.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lol.backend.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByNameAndTag(String name, String tag);
}