package com.github.bartcowski.gymkeeper.domain.user;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(long id) {

}
