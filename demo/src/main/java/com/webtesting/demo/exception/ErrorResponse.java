package com.webtesting.demo.exception;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
}
