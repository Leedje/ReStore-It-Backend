package com.restoreit.services;

import com.restoreit.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.restoreit.models.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User CreateUser(User user){
        return userRepository.save(user);
    }
}
