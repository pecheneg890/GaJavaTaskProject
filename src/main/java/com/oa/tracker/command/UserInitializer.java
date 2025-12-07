package com.oa.tracker.command;

import com.oa.tracker.database.categories.Role;
import com.oa.tracker.database.Entity.User;
import com.oa.tracker.database.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/***
 * Инициализация начального пользователя
 */
@Component
public class UserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        final String ADMIN_EMAIL = "admin@admin";
        final String ADMIN_PASS = "admin";

        if (!userRepository.existsById(ADMIN_EMAIL)) {
            User user = new User(
                    ADMIN_EMAIL,
                    "",
                    "",
                    passwordEncoder.encode(ADMIN_PASS),
                    Role.ROLE_ADMIN
            );
            userRepository.save(user);
        }
    }
}
