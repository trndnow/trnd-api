package com.trnd.trndapi.security.service;

import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.entity.Merchant;
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
import com.trnd.trndapi.security.playload.response.MessageResponse;
import com.trnd.trndapi.security.playload.response.TokenRefreshResponse;
import com.trnd.trndapi.service.*;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

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

    @Transactional
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {

        if(signupRequest.getRole().equals(ERole.ROLE_MERCHANT) && signupRequest.getMerchantName().isEmpty()){
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Merchant Name is mandatory to register as merchant.")
                    .statusCode(HttpStatus.BAD_REQUEST.toString()).build()
            );
        }
        if(signupRequest.getRole().equals(ERole.ROLE_AFFILIATE) && signupRequest.getMerchantCode().isEmpty()){
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message("Merchant Code is mandatory to register as Affiliate.")
                    .statusCode(HttpStatus.BAD_REQUEST.toString()).build());
        }
        if(signupRequest.getRole().equals(ERole.ROLE_AFFILIATE) && !signupRequest.getMerchantCode().isEmpty()){
            /** CHECK IF MERCHANT_CODE IS VALID
             */
            Optional<Merchant> byMerchantCode = merchantRepository.findByMerchantCode(signupRequest.getMerchantCode());
            if(byMerchantCode.isEmpty()){
                return ResponseEntity.badRequest().body(MessageResponse.builder()
                        .message("Merchant Code is invalid").statusCode(HttpStatus.BAD_REQUEST.toString()).build());
            }
        }

        if (userRepository.existsByMobile(signupRequest.getMobile())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Mobile is already exist"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        if(!otpService.validateOtp(signupRequest.getEmail(),signupRequest.getOtp()) && !signupRequest.getRole().name().equalsIgnoreCase(ERole.ROLE_ADMIN.toString())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: OTP Expired!"));
        }
        //TODO: Check if Admin already exist dont create the account.
        if(signupRequest.getRole().equals(ERole.ROLE_ADMIN)){
            Boolean userByRoleName = userRepository.existsUserByRoleName(signupRequest.getRole());
            if(userByRoleName)
                return ResponseEntity.badRequest().body( new MessageResponse("Cannot register as admin role"));
        }
        //     String
        AtomicReference<String> currentActiveRole = new AtomicReference<>();
        if(signupRequest.getRole().name().isEmpty()){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Role cannot be empty"));
        }
        Optional<Role> userRole = Optional.empty();
        switch (signupRequest.getRole()) {
            case ROLE_ADMIN:
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                currentActiveRole.set("ADMIN");
                 userRole = Optional.ofNullable(adminRole);
                break;
            case ROLE_MERCHANT:
                Role merchantRole = roleRepository.findByName(ERole.ROLE_MERCHANT)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                currentActiveRole.set("MERCHANT");
                userRole = Optional.ofNullable(merchantRole);
                break;
            case ROLE_AFFILIATE:
                Role affiliateRole = roleRepository.findByName(ERole.ROLE_AFFILIATE)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                currentActiveRole.set("AFFILIATE");
                userRole = Optional.ofNullable(affiliateRole);
                break;
            default:
                throw new RuntimeException("Error: Role is not found");
        }


        String uniqueCode = codeGeneratorService.generateUniqueCode(currentActiveRole.get().toString());

        User user = User.builder()
                .email(signupRequest.getEmail())
                .uniqueId(UUID.randomUUID())
                .userCode(uniqueCode)
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .mobile(signupRequest.getMobile())
                .userStatus(AccountStatus.INACTIVE)
                .softDeleted(false)
                .emailVerifiedFlag(true)
                .registrationDateTime(LocalDateTime.now())
                .role(userRole.get())
                .build();
        if(signupRequest.getRole().equals(ERole.ROLE_ADMIN)){
            user.setUserStatus(AccountStatus.ACTIVE);
            user.setEmailVerifiedFlag(true);
        }

        //TODO: Call user service
        User savedUser =  userRepository.save(user);

//        String jwtToken = jwtUtils.generateToken(UserDetailsImpl.build(user));
//        String refreshToken = jwtUtils.generateRefreshToken(UserDetailsImpl.build(user));
//        savedUserToken(savedUser, jwtToken);

        /**:Trigger User registration event */
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(savedUser, signupRequest);
        applicationEventPublisher.publishEvent(userCreatedEvent);


        return   ResponseEntity.ok(ResponseDto.builder()
                .code(HttpStatus.CREATED.value())
                .statusCode(HttpStatus.CREATED.toString())
                .statusMsg("User Created Successfully")
                        .data(
                                JwtResponse.builder()
                                        .username(savedUser.getMobile())
                                        .email(savedUser.getEmail())
                                        .uuid(savedUser.getUniqueId())
//                                      .accessToken(jwtToken)
//                                      .refreshToken(refreshToken)
                                        .role(savedUser.getRole().getName())
                                        .build()
                        )
                .build());

    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        User savedUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new UsernameNotFoundException("Error: User not found."));

        if(savedUser.getUserStatus().equals(AccountStatus.INACTIVE) && !savedUser.getRole().getName().equals(ERole.ROLE_ADMIN)){
            return ResponseEntity.ok(MessageResponse.builder().message("Account activation pending from trndnow").build());
        }
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
                .role(savedUser.getRole().getName())
                .build();

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
