package com.github.bartcowski.gymkeeper.domain.user;

public record Username(String username) {

    public Username {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
    }
}
