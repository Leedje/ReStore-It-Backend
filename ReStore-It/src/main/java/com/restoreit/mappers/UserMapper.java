package com.restoreit.mappers;

import com.restoreit.dtos.UserDTO;
import com.restoreit.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper (uses = ProductMapper.class, componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User DTOToUser(UserDTO userDTO);
    UserDTO UserToDTO(User user);
}
