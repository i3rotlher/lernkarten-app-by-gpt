package com.lernkartenapp.controller;

import com.lernkartenapp.model.User;
import com.lernkartenapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        String jwt = userService.login(user.getEmail(), user.getPassword());
        if (jwt != null) {
            return ResponseEntity.ok(jwt);
        }
        return ResponseEntity.status(401).build(); // Unauthorized
    }
}
