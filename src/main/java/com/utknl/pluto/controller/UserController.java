package com.utknl.pluto.controller;

import com.utknl.pluto.model.UserRequest;
import com.utknl.pluto.model.UserResponse;
import com.utknl.pluto.model.error.ErrorResponse;
import com.utknl.pluto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Login as merchant user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    })
    @PostMapping(path = "/user/login", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> userLogin(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ErrorResponse.create(bindingResult), HttpStatus.BAD_REQUEST);
        }

        return userService.userLogin(userRequest)
                .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity(ErrorResponse.create(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
