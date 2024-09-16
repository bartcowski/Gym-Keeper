package com.github.bartcowski.gymkeeper.infrastructure.storage.user;

import com.github.bartcowski.gymkeeper.domain.user.User;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.user.UserRepository;
import com.github.bartcowski.gymkeeper.domain.user.Username;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("prod")
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return jpaUserRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(UserId userId) {
        return jpaUserRepository.findById(userId);
    }

    @Override
    public Optional<User> findUserByName(Username username) {
        return jpaUserRepository.findByUsername(username);
    }

    @Override
    public User addUser(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public void deleteUser(UserId userId) {
        jpaUserRepository.deleteById(userId);
    }
}
