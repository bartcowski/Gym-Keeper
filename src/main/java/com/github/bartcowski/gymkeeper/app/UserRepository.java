package com.github.bartcowski.gymkeeper.app;

import com.github.bartcowski.gymkeeper.domain.user.User;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.user.Username;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAllUsers();

    Optional<User> findUserById(UserId userId);

    Optional<User> findUserByName(Username username);

    void addUser(User user);

    void deleteUser(UserId userId);

    void updateUser(User user);

}
