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

        User user = User.builder().email("admin@gmail.com")
                .firstname("admin")
                .lastname("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();

        userRepo.save(user);
    }
}
