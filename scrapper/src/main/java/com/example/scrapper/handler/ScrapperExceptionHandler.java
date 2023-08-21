package com.example.scrapper.handler;

import com.example.scrapper.annotation.ScrapperHandler;
import com.example.scrapper.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Objects;

@RestControllerAdvice(annotations = ScrapperHandler.class)
public class ScrapperExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ClIENT_ERROR_RESPONSE = "client";
    public static final String SERVER_ERROR_RESPONSE = "server";
    public static final String ClIENT_DESCRIPTION = "wrong client information";
    public static final String SERVER_DESCRIPTION = "Internal server";

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ApiErrorResponse> clientHandleException(TelegramApiException ex) {
        return build(ex, HttpStatus.BAD_REQUEST, ClIENT_DESCRIPTION, ClIENT_ERROR_RESPONSE);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> serverHandleException(TelegramApiException ex) {
        return build(ex, HttpStatus.INTERNAL_SERVER_ERROR, SERVER_ERROR_RESPONSE, SERVER_DESCRIPTION);
    }

    private ResponseEntity<ApiErrorResponse> build(Exception ex, HttpStatus httpStatus,
                                                   String code, String description){
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        description,
                        code,
                        ex.toString(),
                        ex.getMessage(),
                        Arrays.stream(ex.getStackTrace())
                                .map(Objects::toString)
                                .toList()
                ),
                httpStatus
        );

    }
}
