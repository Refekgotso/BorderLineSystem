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
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standard response envelope every controller endpoint should return, so
 * clients always see the same shape: {status, message, data, timestamp}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private int status;
    private String message;
    private T data;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> Response<T> success(int status, String message, T data) {
        return Response.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> Response<T> error(int status, String message) {
        return Response.<T>builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
