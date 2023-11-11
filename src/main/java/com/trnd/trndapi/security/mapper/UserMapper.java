package com.trnd.trndapi.security.mapper;

import com.trnd.trndapi.security.dto.UserDto;
import com.trnd.trndapi.security.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toEntity(UserDto userDto);


}
