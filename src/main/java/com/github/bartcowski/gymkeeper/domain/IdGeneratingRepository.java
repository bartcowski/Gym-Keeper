package com.github.bartcowski.gymkeeper.domain;

import io.hypersistence.tsid.TSID;

public interface IdGeneratingRepository {

    default long nextIdentity() {
        return TSID.fast().toLong();
    }

}
