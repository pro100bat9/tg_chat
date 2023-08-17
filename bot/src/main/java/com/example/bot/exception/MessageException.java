package com.example.bot.exception;

public class MessageException extends RuntimeException{
    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Long id, Throwable cause) {
        this("Error sending message with id " + id, cause);
    }
}
