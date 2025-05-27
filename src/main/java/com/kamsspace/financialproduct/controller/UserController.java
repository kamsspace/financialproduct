package com.kamsspace.financialproduct.controller;

import com.kamsspace.financialproduct.model.User;
import com.kamsspace.financialproduct.payload.UserDTO;
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
    public ResponseEntity<UserDTO> createUsers(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUserDTO = userService.createUser(userDTO);
        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId) {
         UserDTO deletedUser = userService.deleteUser(userId);
         return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }

    @PutMapping("/public/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long userId) {
        UserDTO savedUserDTO = userService.updateUser(userDTO, userId);
        return new ResponseEntity<>(savedUserDTO, HttpStatus.OK);
    }

    @GetMapping("/public/users/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}
