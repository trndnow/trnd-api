package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.dto.UserDto;
import com.trnd.trndapi.enums.ERole;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<?> getPendingApproval(ERole role);
    ResponseDto approveUser(List<UserDto> userDtoList, ERole role);
}
