package com.kamsspace.financialproduct.service;

import com.kamsspace.financialproduct.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void createUser(User user);

    String deleteUser(Long userId);

    User updateUser(User user, Long userId);

    User getUserById(Long userId);

}
