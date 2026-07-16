package com.BorderLineSystem.BorderLine.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Response<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(200, message, data, LocalDateTime.now());
    }
}