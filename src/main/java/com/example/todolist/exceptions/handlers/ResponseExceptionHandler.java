package com.example.todolist.exceptions.handlers;

import com.example.todolist.exceptions.types.DuplicateValueException;
import com.example.todolist.exceptions.types.LimitException;
import com.example.todolist.utils.response.ErrorResponseBody;
import com.example.todolist.exceptions.types.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, ServletWebRequest request) {

        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());

        ErrorResponseBody body = ErrorResponseBody
                .builder()
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .error(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .path(request.getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = { NotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNotFound(
            RuntimeException ex, ServletWebRequest request) {

        log.error("Not Found Exception: {}", ex.getMessage());

        ErrorResponseBody body = ErrorResponseBody
                .builder()
                .status(HttpServletResponse.SC_NOT_FOUND)
                .error(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .path(request.getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(value = { LimitException.class, DuplicateValueException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleLimitError(
            RuntimeException ex, ServletWebRequest request) {

        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());

        ErrorResponseBody body = ErrorResponseBody
                .builder()
                .status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(String.format("Something went wrong: %s", ex.getMessage()))
                .path(request.getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleError(
            RuntimeException ex, ServletWebRequest request) {

        log.error("Server Error: {}", ex.getMessage());

        ErrorResponseBody body = ErrorResponseBody
                .builder()
                .status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Unexpected Error")
                .path(request.getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(
                ex,
                body,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

}
