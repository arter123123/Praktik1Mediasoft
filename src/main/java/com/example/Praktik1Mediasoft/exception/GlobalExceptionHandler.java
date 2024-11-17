package com.example.Praktik1Mediasoft.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Глобальный обработчик исключений для всего приложения.
 * Обрабатывает различные ошибки, включая валидацию и другие неожиданные исключения.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает ошибки валидации для аргументов метода.
     *
     * @param ex Исключение, возникающее при ошибках валидации.
     * @return Ответ с ошибками валидации.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error ->
                errorMessages.append(error.getDefaultMessage()).append(", ")
        );
        return new ResponseEntity<>(errorMessages.toString(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключения, возникающие при неверных аргументах.
     *
     * @param ex Исключение, возникающее при неверных аргументах.
     * @return Ответ с ошибкой.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает ошибки нарушения ограничений.
     *
     * @param ex Исключение, возникающее при нарушении ограничений.
     * @return Ответ с ошибкой.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает все другие неожиданные исключения.
     *
     * @param ex Исключение, возникающее в процессе работы приложения.
     * @return Ответ с кодом ошибки.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
