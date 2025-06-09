package com.restoreit.services;

import com.restoreit.dtos.UserDTO;
import com.restoreit.models.User;
import com.restoreit.mappers.UserMapper;
import com.restoreit.repository.ProductRepository;
import com.restoreit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public UserDTO GetUserByID(UUID userID){
       return this.userRepository.findById(userID)
               .map(userMapper::UserToDTO)
               .orElse(null);
    }

    public UserDTO GetUserByEmail(String email){
        return this.userMapper.UserToDTO(this.userRepository.findByEmail(email));
    }

    public User GetUserByProductId(UUID productId) {
        return userRepository.findByProductId(productId);
    }
}
