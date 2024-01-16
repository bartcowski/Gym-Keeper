package com.github.bartcowski.gymkeeper.infrastructure.web;

import com.github.bartcowski.gymkeeper.app.UserIndexesProvider;
import com.github.bartcowski.gymkeeper.app.UserService;
import com.github.bartcowski.gymkeeper.domain.user.*;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserIndexesProvider userIndexesProvider;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.findAllUsers()
                .stream()
                .map(UserDTO::fromDomain)
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long userId) {
        Optional<User> user = userService.findUserById(new UserId(userId));

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = user.map(UserDTO::fromDomain).get();
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<UserDTO> getUserByUsername(@PathParam("username") String username) {
        Optional<User> user = userService.findUserByName(new Username(username));

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = user.map(UserDTO::fromDomain).get();
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{userId}/BMI")
    public ResponseEntity<UserIndexDTO> getUserBMI(@PathVariable long userId) {
        double userBMI = userIndexesProvider.getUserBMI(new UserId(userId));
        return ResponseEntity.ok(new UserIndexDTO(userBMI));
    }

    @GetMapping("/{userId}/FFMI")
    public ResponseEntity<UserIndexDTO> getUserBMI(@PathVariable long userId, @RequestParam int bodyFatPercentage) {
        double userFFMI = userIndexesProvider.getUserFFMI(new UserId(userId), new BodyFatPercentage(bodyFatPercentage));
        return ResponseEntity.ok(new UserIndexDTO(userFFMI));
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody CreateUserDTO createUserDTO) {
        CreateUserCommand createUserCommand = createUserDTO.toDomain();
        User createdUser = userService.addUser(createUserCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.fromDomain(createdUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        userService.deleteUser(new UserId(userId));
        return ResponseEntity.ok().build();
    }

}
