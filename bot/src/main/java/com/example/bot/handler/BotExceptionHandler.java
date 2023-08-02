package com.example.bot.handler;


import com.example.bot.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;

@RestControllerAdvice
public class BotExceptionHandler {
    public static final String BAD_REQUEST_DESCRIPTION = "Bad request";
    public static final String BAD_REQUEST_CODE = "400";

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> badRequestHandle(HttpClientErrorException.BadRequest ex){
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        BAD_REQUEST_DESCRIPTION,
                        BAD_REQUEST_CODE,
                        ex.getClass().getName(),
                        ex.getMessage(),
                        Arrays.stream(ex.getStackTrace()).map(Object::toString).toList()),
                HttpStatus.BAD_REQUEST
        );
    }
}
