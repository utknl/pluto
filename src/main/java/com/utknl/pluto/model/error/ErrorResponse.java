package com.utknl.pluto.model.error;

import com.utknl.pluto.util.ErrorUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private List<BaseError> errors;

    public static ErrorResponse create() {
        return ErrorResponse.create(new ApiError());
    }

    public static ErrorResponse create(BaseError error) {
        return new ErrorResponse(Collections.singletonList(error));
    }

    public static ErrorResponse create(BindingResult bindingResult) {
        return new ErrorResponse(ErrorUtils.getBindingResultErrors(bindingResult));
    }

}
