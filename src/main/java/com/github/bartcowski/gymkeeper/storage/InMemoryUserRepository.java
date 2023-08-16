package com.github.bartcowski.gymkeeper.storage;

import com.github.bartcowski.gymkeeper.app.UserRepository;
import com.github.bartcowski.gymkeeper.domain.user.User;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.user.Username;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void addUser(User user) {
        usersMap.put(user.getId(), user);
    }

    @Override
    public void deleteUser(UserId userId) {
        usersMap.remove(userId);
    }

    @Override
    public void updateUser(User user) {
        usersMap.put(user.getId(), user);
    }
}
