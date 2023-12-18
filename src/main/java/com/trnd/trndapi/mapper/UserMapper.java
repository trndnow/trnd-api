package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.UserDto;
import com.trnd.trndapi.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toEntity(UserDto userDto);

    @Mapping(source = "emailVerifiedFlag", target = "isEmailVerified")
    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
    List<UserDto> toDtoList(List<User> userList);
}