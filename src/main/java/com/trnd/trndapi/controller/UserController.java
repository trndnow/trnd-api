package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.UserDto;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/merchantPendingApproval")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPendingApproval(){
        List<UserDto> pendingApproval = userService.getPendingApproval(ERole.ROLE_MERCHANT);
        return ResponseEntity.ok(pendingApproval);
    }

    @PostMapping("/approveMerchant")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveMerchant(@RequestBody List<UserDto> userDtoList){
        List<UserDto> approvedUser = userService.approveUser(userDtoList, ERole.ROLE_MERCHANT);
        return ResponseEntity.ok(approvedUser);
    }

    @PostMapping("/approveAffiliate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<?> approveAffiliate(@RequestBody List<UserDto> userDtoList){
        List<UserDto> approvedUser = userService.approveUser(userDtoList,ERole.ROLE_AFFILIATE);
        return ResponseEntity.ok(approvedUser);
    }

    @GetMapping("/affiliatePendingApproval")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<?> getAffiliatePendingApproval(){
        List<UserDto> pendingApproval = userService.getPendingApproval(ERole.ROLE_AFFILIATE);
        return ResponseEntity.ok(pendingApproval);
    }

}
