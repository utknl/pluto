package com.utknl.pluto.util;


import com.utknl.pluto.model.error.BaseError;
import com.utknl.pluto.model.error.BindingError;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public final class ErrorUtils {

    public static List<BaseError> getBindingResultErrors(BindingResult bindingResult) {

        return bindingResult.getFieldErrors()
                .stream()
                .map(error -> new BindingError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

    }

}
