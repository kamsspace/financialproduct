package com.kamsspace.financialproduct.service;

import com.kamsspace.financialproduct.exception.APIException;
import com.kamsspace.financialproduct.exception.ResourceNotFoundException;
import com.kamsspace.financialproduct.model.User;
import com.kamsspace.financialproduct.payload.UserDTO;
import com.kamsspace.financialproduct.payload.UserResponse;
import com.kamsspace.financialproduct.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new APIException("No user created till now");
        }

        List<UserDTO> userDTOS = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDTOS);
        return userResponse;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        userRepository.delete(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userId) {

        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        User user = modelMapper.map(userDTO, User.class);
        user.setUserId(userId);
        savedUser  = userRepository.save(user);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        return modelMapper.map(user, UserDTO.class);
    }


}
