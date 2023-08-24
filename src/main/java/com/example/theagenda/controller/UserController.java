package com.example.theagenda.controller;

import com.example.theagenda.entity.User;
import com.example.theagenda.model.AuthenticationRequest;
import com.example.theagenda.model.AuthenticationResponse;
import com.example.theagenda.model.RegisterRequest;
import com.example.theagenda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/auth/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
  @GetMapping(path = "hello")
  public String test() {
      return "This is a test and it works";
  }

    @PostMapping(path = "register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping(path = "login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(userService.login(authenticationRequest));
    }

    @GetMapping(path = "users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }

    @PatchMapping(path = "users/update")
    public ResponseEntity<User> updateUser() {
        return new ResponseEntity<User>(userService.updateUser(), HttpStatus.OK);
    }

    @DeleteMapping(path = "users/delete/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
    }
}
