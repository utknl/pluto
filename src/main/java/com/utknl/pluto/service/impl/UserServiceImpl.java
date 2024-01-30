package com.utknl.pluto.service.impl;

import com.utknl.pluto.config.property.UserServiceProperty;
import com.utknl.pluto.model.UserRequest;
import com.utknl.pluto.model.UserResponse;
import com.utknl.pluto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate;
    private final String loginUrl;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate, UserServiceProperty userServiceProperty) {
        this.restTemplate = restTemplate;
        loginUrl = userServiceProperty.getUrl();
    }

    @Override
    public Optional<UserResponse> userLogin(UserRequest userRequest) {
        ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(
                loginUrl, HttpMethod.POST, new HttpEntity<>(userRequest), UserResponse.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

}
