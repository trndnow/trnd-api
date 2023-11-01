package com.trnd.trndapi.security.service;

import com.trnd.trndapi.security.entity.Role;
import com.trnd.trndapi.security.entity.Token;
import com.trnd.trndapi.security.entity.User;
import com.trnd.trndapi.security.enums.AccountStatus;
import com.trnd.trndapi.security.enums.ERole;
import com.trnd.trndapi.security.enums.TokenType;
import com.trnd.trndapi.security.jwt.JwtUtils;
import com.trnd.trndapi.security.playload.request.LoginRequest;
import com.trnd.trndapi.security.playload.request.SignupRequest;
import com.trnd.trndapi.security.playload.request.TokenRefreshRequest;
import com.trnd.trndapi.security.playload.response.JwtResponse;
import com.trnd.trndapi.security.playload.response.MessageResponse;
import com.trnd.trndapi.security.playload.response.TokenRefreshResponse;
import com.trnd.trndapi.security.repository.RoleRepository;
import com.trnd.trndapi.security.repository.TokenRepository;
import com.trnd.trndapi.security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        if(signupRequest.getRole().contains("merchant") && signupRequest.getMerchantName().isEmpty()){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid Request. Merchant Name is mandatory to register as Merchant."));
        }
        if (userRepository.existsByMobile(signupRequest.getMobile())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Mobile is already exist"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_AFFILIATE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        }else{

            strRoles.forEach(role -> {
                        switch (role) {
                            case "admin":
                                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                                roles.add(adminRole);
                                break;
                            case "merchant":
                                Role dealerRole = roleRepository.findByName(ERole.ROLE_MERCHANT)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                                roles.add(dealerRole);
                                break;
                            default:
                                Role userRole = roleRepository.findByName(ERole.ROLE_AFFILIATE)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                                roles.add(userRole);

                        }

                    }
            );

        }


        User user = User.builder()
                .email(signupRequest.getEmail())
                .uniqueId(UUID.randomUUID())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .mobile(signupRequest.getMobile())
                .accountStatus(AccountStatus.INACTIVE)
                .roles(roles).build();

        User savedUser =  userRepository.save(user);

//        TODO:Trigger merchant registration event

        String jwtToken = jwtUtils.generateToken(UserDetailsImpl.build(user));
        String refreshToken = jwtUtils.generateRefreshToken(UserDetailsImpl.build(user));
        savedUserToken(savedUser, jwtToken);


        return   ResponseEntity.ok(JwtResponse.builder()
                .username(savedUser.getMobile())
                .email(savedUser.getEmail())
                .uuid(savedUser.getUniqueId())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .roles(savedUser.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
                .build());

    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        User savedUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new UsernameNotFoundException("Error: User not found."));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwtToken = jwtUtils.generateToken(UserDetailsImpl.build(savedUser));
        final String refreshToken = jwtUtils.generateToken(UserDetailsImpl.build(savedUser));
        revokeAllUserToken(savedUser);
        savedUserToken(savedUser, jwtToken);

        JwtResponse jwtResponse = JwtResponse.builder()
                .username(savedUser.getMobile())
                .email(savedUser.getEmail())
                .uuid(savedUser.getUniqueId())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .roles(savedUser.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
                .build();
        return   ResponseEntity.ok(jwtResponse);
    }

    private void revokeAllUserToken(User user) {
        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUser(user.getId());
        if(allValidTokenByUser.isEmpty())
            return;

        allValidTokenByUser.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(allValidTokenByUser);
    }

    private void savedUserToken(User savedUser, String jwtToken) {
        Token token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public ResponseEntity<?> refreshToken(TokenRefreshRequest tokenRefreshRequest, HttpServletRequest request) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
        refreshToken = authHeader.substring(7);
        username = jwtUtils.extractUsername(requestRefreshToken);
        if(username != null){
            User savedUser = userRepository.findByMobile(username).orElseThrow(()-> new UsernameNotFoundException("Error: User not found"));
            if(jwtUtils.isTokenValid(refreshToken, UserDetailsImpl.build(savedUser))){
                final String accessToken = jwtUtils.generateToken(UserDetailsImpl.build(savedUser));
                revokeAllUserToken(savedUser);
                savedUserToken(savedUser, accessToken);
                return ResponseEntity.ok(TokenRefreshResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .tokenType(TokenType.BEARER.name())
                        .build()
                );
            }
        }
        return ResponseEntity.ok(new RuntimeException("Invalid Refresh Token"));
    }
}
