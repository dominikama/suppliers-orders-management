package com.stockmanagement.application.services;


import com.stockmanagement.adapters.controller.exceptions.ErrorResponse;

public interface ExceptionHandlingService {

    ErrorResponse createErrorResponse(Exception e);
}
