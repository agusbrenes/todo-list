package com.example.todolist.exceptions.handlers;

import com.example.todolist.enums.UserRole;
import com.example.todolist.utils.response.ErrorResponseBody;
import com.example.todolist.users.entity.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AuthorizationExceptionHandler implements AccessDeniedHandler {

    private static final String NOT_FOUND_MESSAGE = "Not Found";

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex) throws IOException, ServletException {
        log.error("Authorization error: {}", ex.getMessage());

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        boolean isMaintainer = userDetails
                .getRole()
                .getName()
                .equals(UserRole.MAINTAINER.name());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(isMaintainer ?
                HttpServletResponse.SC_FORBIDDEN :
                HttpServletResponse.SC_NOT_FOUND);

        ErrorResponseBody body = ErrorResponseBody
                .builder()
                .status(isMaintainer ?
                        HttpServletResponse.SC_FORBIDDEN :
                        HttpServletResponse.SC_NOT_FOUND)
                .error(isMaintainer ?
                        HttpStatus.FORBIDDEN :
                        HttpStatus.NOT_FOUND)
                .message(isMaintainer ?
                        ex.getMessage() :
                        NOT_FOUND_MESSAGE)
                .path(request.getServletPath())
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}
