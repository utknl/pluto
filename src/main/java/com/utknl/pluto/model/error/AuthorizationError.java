package com.utknl.pluto.model.error;

public class AuthorizationError extends BaseError {

    public AuthorizationError() {
        super("Authorization Failed!");
    }

    public AuthorizationError(String message) {
        super(message);
    }

}
