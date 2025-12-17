package org.example.booking_be.exception;


import org.example.booking_be.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleException {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ApiResponse.builder()
                        .code(e.getErrorCode().getCode())
                        .message(e.getErrorCode().getMessage())
                        .build());
    }
}
