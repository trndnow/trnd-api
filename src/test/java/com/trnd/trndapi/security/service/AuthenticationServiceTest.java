package com.trnd.trndapi.security.service;

import com.trnd.trndapi.dto.ErrorResponse;
import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.entity.Role;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.repository.RoleRepository;
import com.trnd.trndapi.security.playload.request.SignupRequest;
import com.trnd.trndapi.security.playload.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationServiceTest {
  
    @Autowired
    AuthenticationService authService;
  
    @MockBean
    RoleRepository roleRepository;

    /**
     * Testing the registerUser method of the AuthenticationService.
     */
   // @Test
    public void testRegisterUser() {
        // Setup sample data
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("email@example.com");
        signupRequest.setRole(ERole.ROLE_ADMIN);
        signupRequest.setPassword("password123");
        signupRequest.setMobile("1234567890");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        Role expectedRole = new Role();
        expectedRole.setName(ERole.ROLE_ADMIN);
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(expectedRole));

        // Execute the method to test
        ResponseEntity<?> response = authService.registerUser(signupRequest, bindingResult);

        // Assertions
        Assert.isTrue(response.getStatusCode() == HttpStatus.OK, "Response StatusCode should be OK");
        Assert.notNull(response.getBody(), "Response Body should not be null");

        ResponseDto responseDto = (ResponseDto) response.getBody();
        JwtResponse jwtResponse = (JwtResponse) responseDto.getData();
    
        Assert.notNull(jwtResponse, "jwtResponse should not be null");
        Assert.isTrue(jwtResponse.getEmail().equals(signupRequest.getEmail()), "Email should match the signup request");
        Assert.notNull(jwtResponse.getUuid(), "UUID should not be null");
    }

    //@Test
    public void testRegisterUser_ValidationError() {
        // Setup sample data
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("email@example.com");
        signupRequest.setRole(ERole.ROLE_ADMIN);
        signupRequest.setPassword("password123");
        signupRequest.setMobile("1234567890");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(Collections.emptyList());

        // Execute the method to test
        ResponseEntity<?> response = authService.registerUser(signupRequest, bindingResult);

        // Assertions
        Assert.isTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST, "Response StatusCode should be BAD_REQUEST");
        Assert.notNull(response.getBody(), "Response Body should not be null");

        ErrorResponse ErrorResponse = (ErrorResponse) response.getBody();
        Assert.isTrue(ErrorResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST.toString()), "Status Code should be BAD_REQUEST");
    }

   // @Test
    public void testRegisterUser_RoleNotFound() {
        // Setup sample data
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("email@example.com");
        signupRequest.setRole(ERole.ROLE_ADMIN);
        signupRequest.setPassword("password123");
        signupRequest.setMobile("1234567890");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenThrow(new UsernameNotFoundException("Error: Role is not found"));

        // Assertions
        try {
            // Execute the method to test
            authService.registerUser(signupRequest, bindingResult);
        } catch (Exception e) {
            Assert.isTrue(e instanceof UsernameNotFoundException, "Exception should be UsernameNotFoundException");
            Assert.isTrue(e.getMessage().equals("Error: Role is not found"), "Exception message should match");
        }
    }
}