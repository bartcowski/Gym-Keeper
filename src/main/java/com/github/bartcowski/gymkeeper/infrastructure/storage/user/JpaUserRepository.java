package com.github.bartcowski.gymkeeper.infrastructure.storage.user;

import com.github.bartcowski.gymkeeper.domain.user.User;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.user.Username;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("prod")
interface JpaUserRepository extends JpaRepository<User, UserId> {

    Optional<User> findByUsername(Username username);
}
