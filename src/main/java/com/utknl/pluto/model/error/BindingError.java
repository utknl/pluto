package com.utknl.pluto.model.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BindingError extends BaseError {

    private String field;

    public BindingError(String field, String message) {
        super(message);
        this.field = field;
    }

}
