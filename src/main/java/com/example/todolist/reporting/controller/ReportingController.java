package com.example.todolist.reporting.controller;

import com.example.todolist.reporting.service.ReportingService;
import com.example.todolist.utils.response.CountReport;
import com.example.todolist.utils.response.ErrorResponseBody;
import com.example.todolist.utils.response.ResultsReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/reporting", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping(path = "/todo-lists")
    @Operation(summary = "Get all the stored To-Do Lists' titles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the To-Do Lists' titles",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<ResultsReport> getAllTodoLists() {
        return new ResponseEntity<>(reportingService.getAllTodoLists(), HttpStatus.OK);
    }

    @GetMapping(path = "/users")
    @Operation(summary = "Get all the stored Users' emails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Users' emails",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<ResultsReport> getAllUsers() {
        return new ResponseEntity<>(reportingService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/todo-lists/items")
    @Operation(summary = "Get the amount of stored To-Do List Items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the amount of Items",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<CountReport> getTotalItems() {
        return new ResponseEntity<>(reportingService.getTotalItems(), HttpStatus.OK);
    }

}
