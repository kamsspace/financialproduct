package com.kamsspace.financialproduct.controller;

import com.kamsspace.financialproduct.model.User;
import com.kamsspace.financialproduct.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/public/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/public/users")
    public ResponseEntity<String> createUsers(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            String status = userService.deleteUser(userId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/public/users/{userId}")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable Long userId) {
        try {
            User savedUser = userService.updateUser(user, userId);
            return new ResponseEntity<>("User with userId: " + userId, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

}
