package com.github.bartcowski.gymkeeper;

import com.github.bartcowski.gymkeeper.app.UserRepository;
import com.github.bartcowski.gymkeeper.domain.user.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OnLaunchTestDataGenerator implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        CreateUserCommand createUserCommand1 = new CreateUserCommand(
                new Username("kowalski"),
                UserGender.MALE,
                new UserAge(23),
                new UserWeight(90.0),
                new UserHeight(185)
        );
        CreateUserCommand createUserCommand2 = new CreateUserCommand(
                new Username("nowacka"),
                UserGender.FEMALE,
                new UserAge(39),
                new UserWeight(55.1),
                new UserHeight(167)
        );
        userRepository.addUser(createUserCommand1);
        userRepository.addUser(createUserCommand2);
    }

}
