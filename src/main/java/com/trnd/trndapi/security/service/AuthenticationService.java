package com.trnd.trndapi.security.service;

import com.trnd.trndapi.dto.ErrorResponse;
import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.entity.Role;
import com.trnd.trndapi.entity.Token;
import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.enums.TokenType;
import com.trnd.trndapi.events.UserCreatedEvent;
import com.trnd.trndapi.repository.MerchantRepository;
import com.trnd.trndapi.repository.RoleRepository;
import com.trnd.trndapi.repository.TokenRepository;
import com.trnd.trndapi.repository.UserRepository;
import com.trnd.trndapi.security.jwt.JwtUtils;
import com.trnd.trndapi.security.playload.request.LoginRequest;
import com.trnd.trndapi.security.playload.request.SignupRequest;
import com.trnd.trndapi.security.playload.request.TokenRefreshRequest;
import com.trnd.trndapi.security.playload.response.JwtResponse;
import com.trnd.trndapi.security.playload.response.TokenRefreshResponse;
import com.trnd.trndapi.service.*;
import com.trnd.trndapi.validation.UserRegistrationValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private static final int REQUIRED_COMPLETENESS_PERCENTAGE = 5;
    private final MerchantRepository merchantRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final OtpService otpService;
    private final UserCompletenessCalculatorFactory profileCalculatorFactory;
    private final CodeGeneratorService codeGeneratorService;
    public final BusinessServiceCategoryService businessServiceCategoryService;
    private final MerchantService merchantService;
    private final UserRegistrationValidator registrationValidator;

    /**
     * Registers a new user.
     *
     * @param signupRequest the signup request object containing user details
     * @param bindingResult the binding result object for request validation
     *
     * @return the response entity containing the user registration status and information
     */
    @Transactional
    public ResponseEntity<?> registerUser(SignupRequest signupRequest, BindingResult bindingResult) {
        ResponseEntity<?> BAD_REQUEST = validateSignupRequest(signupRequest, bindingResult);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        Role userRole = resolveUserRole(signupRequest);
        String uniqueCode = codeGeneratorService.generateUniqueCode(userRole.getName().name());
        User user = createUser(signupRequest, userRole, uniqueCode);
        updateUserStatusIfAdmin(signupRequest, user);

        //** Call user service */
        User savedUser =  userRepository.save(user);

        /**:Trigger User registration event */
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(savedUser, signupRequest);
        applicationEventPublisher.publishEvent(userCreatedEvent);

        return buildResponse(savedUser);

    }
    private ResponseEntity<?> buildResponse(User savedUser){
        return ResponseEntity.ok(ResponseDto.builder()
                .code(HttpStatus.CREATED.value())
                .statusCode(HttpStatus.CREATED.toString())
                .statusMsg("User Created Successfully")
                .data(
                        JwtResponse.builder()
                                .username(savedUser.getMobile())
                                .email(savedUser.getEmail())
                                .uuid(savedUser.getUniqueId())
                                .role(savedUser.getRole().getName())
                                .build()
                )
                .build());
    }

    private User createUser(SignupRequest signupRequest, Role userRole, String uniqueCode){
        return User.builder()
                .email(signupRequest.getEmail())
                .uniqueId(UUID.randomUUID())
                .userCode(uniqueCode)
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .mobile(signupRequest.getMobile())
                .userStatus(AccountStatus.INACTIVE)
                .softDeleted(false)
                .emailVerifiedFlag(true)
                .registrationDateTime(LocalDateTime.now())
                .role(userRole)
                .build();
    }
    private void updateUserStatusIfAdmin(SignupRequest signupRequest, User user){
        if(signupRequest.getRole().equals(ERole.ROLE_ADMIN)){
            user.setUserStatus(AccountStatus.ACTIVE);
            user.setEmailVerifiedFlag(true);
        }
    }

    private ResponseEntity<?> validateSignupRequest(SignupRequest signupRequest, BindingResult bindingResult) {
        List<String> validationErrors = new ArrayList<>();

        // Add Spring's validation errors
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> validationErrors.add(error.getDefaultMessage()));
        }

        // Add custom validation errors
        validationErrors.addAll(registrationValidator.validate(signupRequest));
        if (!validationErrors.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(
                            ErrorResponse.builder()
                                    .code(HttpStatus.BAD_REQUEST.value())
                                    .statusCode(HttpStatus.BAD_REQUEST.toString())
                                    .statusMsg(validationErrors).build()


                    );
        }

        if(!otpService.validateOtp(signupRequest.getEmail(), signupRequest.getOtp()) && !signupRequest.getRole().name().equalsIgnoreCase(ERole.ROLE_ADMIN.toString())){
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .statusCode(HttpStatus.BAD_REQUEST.toString())
                            .statusMsg("OTP Expired!")
                            .build()
            );
        }
        return null;
    }

    private Role resolveUserRole(SignupRequest signupRequest) {
        Role userRole;
        switch (signupRequest.getRole()) {
            case ROLE_ADMIN:
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                 userRole = adminRole;
                break;
            case ROLE_MERCHANT:
                Role merchantRole = roleRepository.findByName(ERole.ROLE_MERCHANT)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                userRole = merchantRole;
                break;
            case ROLE_AFFILIATE:
                Role affiliateRole = roleRepository.findByName(ERole.ROLE_AFFILIATE)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                userRole = affiliateRole;
                break;
            default:
                throw new RuntimeException("Error: Role is not found");
        }
        return userRole;
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        User savedUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new UsernameNotFoundException("Error: User not found."));

        if(savedUser.getUserStatus().equals(AccountStatus.INACTIVE) && !savedUser.getRole().getName().equals(ERole.ROLE_ADMIN)){
            return ResponseEntity.badRequest().body(ErrorResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .statusCode(HttpStatus.BAD_REQUEST.toString())
                    .statusMsg("Account activation pending from trndnow")
                    .build());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtResponse jwtResponse = createJwtResponse(savedUser);

        /**Calculate user profile completion percentage */
        if(!savedUser.getRole().getName().equals(ERole.ROLE_ADMIN)){
            int profileCompletionPercentage = calculateProfileCompleteness(savedUser);
            if (profileCompletionPercentage > REQUIRED_COMPLETENESS_PERCENTAGE ) {
                jwtResponse.setProfileCompleted(true);
                jwtResponse.setProfileCompletionPercentage(profileCompletionPercentage);
            }
        }
        return   ResponseEntity.ok().body(jwtResponse);
    }

    private JwtResponse createJwtResponse(User savedUser) {
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
                .role(savedUser.getRole().getName())
                .build();
        return jwtResponse;
    }

    private int calculateProfileCompleteness(User savedUser) {
     if(savedUser == null)
         return 0;
        ProfileCompletenessCalculator calculator = profileCalculatorFactory.createCalculator(savedUser);
        int completeness = calculator.calculateProfileCompleteness(savedUser);
        return completeness;
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
