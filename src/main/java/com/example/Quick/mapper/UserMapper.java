package com.example.Quick.mapper;

import com.example.Quick.Entity.User;
import com.example.Quick.dto.RegisterRequest;
import com.example.Quick.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userCredentialId", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "password", ignore = true)
    User registerRequestToUser(RegisterRequest registerRequest);
}
