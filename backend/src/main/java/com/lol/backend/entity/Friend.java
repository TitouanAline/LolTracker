package com.lol.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "friends")
@Getter
@Setter
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String tag;

    public Friend() {
    }

    public Friend(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }
}