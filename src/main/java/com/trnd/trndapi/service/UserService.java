package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.UserDto;
import com.trnd.trndapi.enums.ERole;

import java.util.List;

public interface UserService {

    List<UserDto> getPendingApproval(ERole role);
    List<UserDto> approveUser(List<UserDto> userDtoList, ERole role);
}
