package com.github.bartcowski.gymkeeper.infrastructure.storage;

import com.github.bartcowski.gymkeeper.domain.user.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InMemoryUserRepository implements UserRepository {

    private static long userIdCounter = 0;

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
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public User addUser(CreateUserCommand command) {
        User newUser = new User(
                new UserId(userIdCounter++),
                command.username(),
                command.gender(),
                command.age(),
                command.weight(),
                command.height());
        usersMap.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public void deleteUser(UserId userId) {
        usersMap.remove(userId);
    }

}
