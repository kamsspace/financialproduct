package com.kamsspace.financialproduct.service;

import com.kamsspace.financialproduct.model.User;
import com.kamsspace.financialproduct.payload.UserDTO;
import com.kamsspace.financialproduct.payload.UserResponse;

public interface UserService {

    UserResponse getAllUsers();
    UserDTO createUser(UserDTO userDTO);

    UserDTO deleteUser(Long userId);

    UserDTO updateUser(UserDTO userDTO, Long userId);

    UserDTO getUserById(Long userId);

}
