package com.github.bartcowski.gymkeeper.domain.user;

import jakarta.persistence.Embeddable;

@Embeddable
public record Username(String username) {

    public Username {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
    }
}
