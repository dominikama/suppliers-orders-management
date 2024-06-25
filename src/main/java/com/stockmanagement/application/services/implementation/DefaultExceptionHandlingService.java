package com.stockmanagement.application.services.implementation;

import com.stockmanagement.adapters.controller.exceptions.ErrorResponse;
import com.stockmanagement.application.services.ExceptionHandlingService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class DefaultExceptionHandlingService implements ExceptionHandlingService {

    Map<Class<? extends Exception>, Integer> errorAndResponseCode = Map.of(NoSuchElementException.class, 404,
            IllegalArgumentException.class, 400);
    @Override
    public ErrorResponse createErrorResponse(Exception e) {
        int errorCode = errorAndResponseCode.getOrDefault(e.getClass(), 500);
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage(e.getMessage());
        errorDto.setResponseCode(errorCode);
        return errorDto;
    }
}
