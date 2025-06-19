package com.restoreit.services;

import com.restoreit.dtos.UserDTO;
import com.restoreit.models.User;
import com.restoreit.mappers.UserMapper;
import com.restoreit.repository.ProductRepository;
import com.restoreit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(14);

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public boolean CreateUser(UserDTO userDto){
        if (userRepository.existsByEmail(userDto.email)) {
            return false;
        }

        userDto.password = encoder.encode(userDto.password);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_BUSINESS")
                .build();

    }
}
