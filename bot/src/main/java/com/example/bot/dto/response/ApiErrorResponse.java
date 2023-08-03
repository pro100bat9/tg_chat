package com.example.bot.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ApiErrorResponse(
       String description,
       String code,
       String exceptionName,
       String exceptionMessage,
       List<String> stacktrace
)
{}