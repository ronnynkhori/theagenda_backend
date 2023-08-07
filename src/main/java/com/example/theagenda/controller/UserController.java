package com.example.theagenda.controller;

import com.example.theagenda.model.AuthenticationRequest;
import com.example.theagenda.model.AuthenticationResponse;
import com.example.theagenda.model.RegisterRequest;
import com.example.theagenda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth/v1/")
@RequiredArgsConstructor
public class UserController {

    private  final UserService userService;


    @PostMapping(path = "register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return  ResponseEntity.ok(userService.register(request));
    }

    @PostMapping(path = "login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return  ResponseEntity.ok(userService.login(authenticationRequest));
    }
}
