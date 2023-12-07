package com.example.todolist.auth.controller;

import com.example.todolist.auth.dto.AuthDto;
import com.example.todolist.auth.service.AuthService;
import com.example.todolist.exceptions.types.DuplicateValueException;
import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.utils.JwtUtils;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.utils.response.ErrorResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all the User's To-Do Lists paginated and sorted by Date of creation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the current User's info",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication)
            throws AuthenticationException {
        log.info("Getting currently Logged-In User");
        return new ResponseEntity<>(authService.getCurrentUser(authentication), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    @Operation(summary = "Register a new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered the new User successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Authentication payload supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "Role \"USER\" not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
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
    @Operation(summary = "Login with an existing User account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in with the User account successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Authentication payload supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Bad User Credentials",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "Role \"USER\" not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
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
    @Operation(summary = "Login with an existing User account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged out the current User account",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<Boolean> logoutUser(HttpServletResponse response) {
        log.info("Logged-Out current User");
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/close")
    @Operation(summary = "Register a new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Closed the currently logged in User's account successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<Boolean> closeAccount(Authentication authentication)
            throws AuthenticationException {
        log.info("Closing current User's account");
        return new ResponseEntity<>(authService.closeAccount(authentication), HttpStatus.OK);
    }

}
