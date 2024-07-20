package com.example.demo.exception;

import com.example.demo.dto.response.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<APIResponse> handleRunTimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(new APIResponse(400, e.getMessage(), null));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<APIResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        return ResponseEntity.badRequest().body(new APIResponse(errorCode.getCode(), errorCode.getMessage(), null));
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse> handleAppException(AppException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatusCode()).body(new APIResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), null));
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<APIResponse> handleException(Exception e) {
        return ResponseEntity.badRequest().body(new APIResponse(ErrorCode.UNCATEGORIZED.getCode(), e.getMessage(), null));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<APIResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(APIResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage()).build());
    }

}
