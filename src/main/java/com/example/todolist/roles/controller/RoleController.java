package com.example.todolist.roles.controller;

import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.roles.dto.RoleInfoDto;
import com.example.todolist.roles.service.RoleService;
import com.example.todolist.utils.response.ErrorResponseBody;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Get all Roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Roles",
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
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get all Roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the specified Role by its id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
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
    public ResponseEntity<RoleDto> getRoleById(@PathVariable("id") Integer id)
            throws RoleNotFoundException {
        return new ResponseEntity<>(roleService.getRole(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new Role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the new Role successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Role payload supplied",
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
    public ResponseEntity<RoleDto> createRole(@Validated @RequestBody RoleInfoDto roleInfoDto) {
        return new ResponseEntity<>(roleService.createRole(roleInfoDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update an existing Role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated the existing Role successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Role payload supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<RoleDto> updateRole(
            @PathVariable("id") Integer id,
            @Validated @RequestBody RoleInfoDto roleInfoDto) throws RoleNotFoundException {
        return new ResponseEntity<>(roleService.updateRole(id, roleInfoDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete an existing To-Do List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deleted the existing Role successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "Endpoint Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<Boolean> deleteRole(@PathVariable("id") Integer id)
            throws RoleNotFoundException {
        roleService.deleteRole(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
