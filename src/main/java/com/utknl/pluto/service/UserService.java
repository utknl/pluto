package com.utknl.pluto.service;

import com.utknl.pluto.model.UserRequest;
import com.utknl.pluto.model.UserResponse;

import java.util.Optional;

public interface UserService {

    Optional<UserResponse> userLogin(UserRequest userRequest);

}
