package com.webtesting.demo.util;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUtil<T> {
    
    private int code;
    private String message;
    private T data;
    
    public static <T> ResponseUtil<T> success(String message, T data) {
        return ResponseUtil.<T>builder()
                .code(200)
                .message(message)
                .data(data)
                .build();
    }
    
    public static <T> ResponseUtil<T> success(T data) {
        return ResponseUtil.<T>builder()
                .code(200)
                .message("Success")
                .data(data)
                .build();
    }
    
    public static <T> ResponseUtil<T> error(int code, String message) {
        return ResponseUtil.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}
