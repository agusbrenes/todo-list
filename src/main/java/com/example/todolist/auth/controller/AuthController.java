package com.example.todolist.auth.controller;

import com.example.todolist.auth.dto.AuthDto;
import com.example.todolist.auth.service.AuthService;
import com.example.todolist.exceptions.types.DuplicateValueException;
import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.utils.JwtUtils;
import com.example.todolist.users.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final JwtUtils jwtUtils;

    private final AuthService authService;

    @GetMapping(path = "/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication)
            throws AuthenticationException {
        log.info("Getting currently Logged-In User");
        return new ResponseEntity<>(authService.getCurrentUser(authentication), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Boolean> registerUser(@Validated @RequestBody AuthDto authDto,
                                                HttpServletResponse response)
            throws DuplicateValueException, RoleNotFoundException {
        log.info("Register new User with email: '{}'", authDto.getEmail());
        boolean success = authService.registerUser(authDto);
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(authDto.getEmail());
        response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        return new ResponseEntity<>(success, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserDto> loginUser(
            @Validated @RequestBody AuthDto authDto,
            HttpServletResponse response) throws AuthenticationException {
        UserDto userDto = authService.loginUser(authDto);
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDto.getEmail());
        response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        log.info("Logged-In User with email: '{}'", userDto.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Boolean> logoutUser(HttpServletResponse response) {
        log.info("Logged-Out current User");
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/close")
    public ResponseEntity<Boolean> closeAccount(Authentication authentication)
            throws AuthenticationException {
        log.info("Closing current User's account");
        return new ResponseEntity<>(authService.closeAccount(authentication), HttpStatus.OK);
    }

}
