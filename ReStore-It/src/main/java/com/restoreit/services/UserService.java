package com.restoreit.services;

import com.restoreit.dtos.UserDTO;
import com.restoreit.models.User;
import com.restoreit.mappers.UserMapper;
import com.restoreit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public boolean CreateUser(UserDTO userDto){
        return userRepository.save(userMapper.DTOToUser(userDto)) != null;
    }
}
