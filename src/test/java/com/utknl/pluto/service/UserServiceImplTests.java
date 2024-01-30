package com.utknl.pluto.service;

import com.utknl.pluto.config.property.UserServiceProperty;
import com.utknl.pluto.model.UserRequest;
import com.utknl.pluto.model.UserResponse;
import com.utknl.pluto.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {
    @Mock
    private RestTemplate restTemplate;
    private UserService userService;
    UserServiceProperty userServiceProperty = new UserServiceProperty("http://example.com");

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(restTemplate, userServiceProperty);
    }

    @Test
    void shouldReturnJWTTokenForValidUser() {
        UserRequest userRequest = UserRequest.builder().email("email").password("password").build();
        UserResponse userResponse = UserResponse.builder().token("token").build();
        ResponseEntity<UserResponse> responseEntity = new ResponseEntity<>(userResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                userServiceProperty.getUrl(), HttpMethod.POST, new HttpEntity<>(userRequest), UserResponse.class))
                .thenReturn(responseEntity);

        Optional<UserResponse> result = userService.userLogin(userRequest);

        verify(restTemplate, times(1)).exchange(
                eq(userServiceProperty.getUrl()), eq(HttpMethod.POST), eq(new HttpEntity<>(userRequest)), eq(UserResponse.class));
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("token", result.get().getToken());
    }

}
