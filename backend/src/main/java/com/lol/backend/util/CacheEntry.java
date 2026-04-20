package com.lol.backend.util;

import java.time.Instant;

public class CacheEntry<T> {
    private final T value;
    private final Instant expiresAt;

    public CacheEntry(T value, Instant expiresAt) {
        this.value = value;
        this.expiresAt = expiresAt;
    }

    public T getValue() {
        return value;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}