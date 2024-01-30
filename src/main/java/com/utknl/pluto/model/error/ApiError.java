package com.utknl.pluto.model.error;

public class ApiError extends BaseError {

    public ApiError() {
        super("Invalid Request!");
    }

    public ApiError(String message) {
        super(message);
    }

}
