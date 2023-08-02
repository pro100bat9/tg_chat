package com.example.scrapper.handler;

import com.example.scrapper.annotation.ScrapperHandler;
import com.example.scrapper.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;

@RestControllerAdvice(annotations = ScrapperHandler.class)
public class ScrapperExceptionHandler {
    private static final String BAD_REQUEST_DESCRIPTION = "Bad request";
    public static final String BAD_REQUEST_CODE = "400";
    public static final String NOT_FOUND_DESCRIPTION = "Not found";
    public static final String NOT_FOUND_CODE = "404";

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

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> notFoundHandle(HttpClientErrorException.NotFound ex){
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        NOT_FOUND_DESCRIPTION,
                        NOT_FOUND_CODE,
                        ex.getClass().getName(),
                        ex.getMessage(),
                        Arrays.stream(ex.getStackTrace()).map(Object::toString).toList()),
                HttpStatus.NOT_FOUND
        );
    }
}
