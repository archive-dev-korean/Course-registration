package Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * 500 등 서버 에러 시 원인 확인을 위해 예외 메시지를 응답에 포함합니다.
 * 원인 파악 후 필요하면 제거하거나 메시지를 숨기면 됩니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        // ResponseStatusException은 그대로 해당 상태로 전달
        if (ex instanceof ResponseStatusException rse) {
            throw rse;
        }
        String message = ex.getMessage();
        Throwable cause = ex.getCause();
        while (cause != null) {
            message = cause.getMessage();
            cause = cause.getCause();
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal Server Error", "message", message != null ? message : ex.getClass().getSimpleName()));
    }
}
