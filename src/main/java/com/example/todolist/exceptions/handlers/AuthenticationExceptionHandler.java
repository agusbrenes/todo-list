package com.example.todolist.exceptions.handlers;

import com.example.todolist.utils.response.ErrorResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @Override
    @ExceptionHandler(value = { BadCredentialsException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        log.error("Authentication error: {}", ex.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponseBody body = ErrorResponseBody
                .builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .error(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .path(request.getServletPath())
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}
