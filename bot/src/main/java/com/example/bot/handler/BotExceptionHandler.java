package com.example.bot.handler;


import com.example.bot.annotation.BotHandler;
import com.example.bot.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Objects;

@RestControllerAdvice(annotations = BotHandler.class)
public class BotExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String TG_API_ERROR_RESPONSE = "tg-api";
    public static final String SERVER_ERROR_RESPONSE = "server";
    public static final String TG_API_DESCRIPTION = "Error with sending message";
    public static final String SERVER_DESCRIPTION = "Internal server";

//    @ExceptionHandler(value = {TelegramApiException.class})
//    public ResponseEntity<ApiErrorResponse> telegramApiHandleException(TelegramApiException ex) {
//        return build(ex, HttpStatus.BAD_REQUEST, TG_API_DESCRIPTION, TG_API_ERROR_RESPONSE);
//
//    }
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
