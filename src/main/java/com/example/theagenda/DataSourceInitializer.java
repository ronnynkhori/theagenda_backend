package com.example.theagenda;

import com.example.theagenda.UserRepo.UserRepo;
import com.example.theagenda.entity.User;
import com.example.theagenda.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSourceInitializer implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {

        // Check if a user with the given email already exists
        boolean userExists = userRepo.findByEmail("admin@mail.com").isPresent();

        // If the user doesn't exist, create and save the new user
        if (!userExists) {
            User user = User.builder().email("admin@mail.com")
                    .firstname("admin")
                    .lastname("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();

            userRepo.save(user);
        } else {
            // Optionally, log or handle the case where the user already exists
            System.out.println("User already exists.");
        }
    }
}
