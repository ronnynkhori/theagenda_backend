package com.example.theagenda.service;

import com.example.theagenda.UserRepo.UserRepo;
import com.example.theagenda.entity.User;
import com.example.theagenda.enums.Role;
import com.example.theagenda.jwt.JwtTokenProvider;
import com.example.theagenda.model.AuthenticationRequest;
import com.example.theagenda.model.AuthenticationResponse;
import com.example.theagenda.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repo.save(user);

        var jwtToken = jwtTokenProvider.generateToken(user);

        return AuthenticationResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole().toString())
                .email(user.getEmail())
                .token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()
        ));

        var user = repo.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        var jwtToken = jwtTokenProvider.generateToken(user);
        return AuthenticationResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().toString())
                .email(user.getEmail())
                .token(jwtToken).build();
    }



    public void deleteUser(Integer id) {
        var user = repo.findById(id);
        if(!user.isEmpty()){
            repo.deleteById(id);
        }
    }

    public User updateUser() {
        return null;
    }

    public User getUserById(Integer id) {
        Optional<User> userOptional = repo.findById(id);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    public List<User> getAllUsers() {
       return repo.findAll();
    }
}
