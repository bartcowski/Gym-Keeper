package com.github.bartcowski.gymkeeper.domain.weightlog;

import jakarta.persistence.Embeddable;

@Embeddable
public record WeightLogId(long id) {
}
