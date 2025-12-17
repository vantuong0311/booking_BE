package org.example.booking_be.exception;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.booking_be.enums.ErrorCode;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException {
    ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}