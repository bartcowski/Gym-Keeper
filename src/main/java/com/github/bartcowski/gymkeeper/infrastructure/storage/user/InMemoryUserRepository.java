package com.github.bartcowski.gymkeeper.infrastructure.storage.user;

import com.github.bartcowski.gymkeeper.domain.user.User;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.user.UserRepository;
import com.github.bartcowski.gymkeeper.domain.user.Username;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("test")
public class InMemoryUserRepository implements UserRepository {

    private final Map<UserId, User> usersMap = new HashMap<>();

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(usersMap.values());
    }

    @Override
    public Optional<User> findUserById(UserId userId) {
        return Optional.ofNullable(usersMap.get(userId));
    }

    @Override
    public Optional<User> findUserByName(Username username) {
        return usersMap.values().stream()
                .filter(user -> user.username().equals(username))
                .findFirst();
    }

    @Override
    public User addUser(User user) {
        usersMap.put(user.id(), user);
        return user;
    }

    @Override
    public void deleteUser(UserId userId) {
        usersMap.remove(userId);
    }
}
