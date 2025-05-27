package com.kamsspace.financialproduct.controller;

import com.kamsspace.financialproduct.model.User;
import com.kamsspace.financialproduct.payload.UserResponse;
import com.kamsspace.financialproduct.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/public/users")
    public ResponseEntity<UserResponse> getAllUsers() {
        UserResponse userResponse = userService.getAllUsers();
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/public/users")
    public ResponseEntity<String> createUsers(@Valid @RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
         String status = userService.deleteUser(userId);
         return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/public/users/{userId}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user, @PathVariable Long userId) {
        User savedUser = userService.updateUser(user, userId);
        return new ResponseEntity<>("User with userId: " + userId, HttpStatus.OK);
    }

}
