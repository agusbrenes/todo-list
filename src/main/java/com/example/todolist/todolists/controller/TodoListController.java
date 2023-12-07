package com.example.todolist.todolists.controller;

import com.example.todolist.auth.service.AuthService;
import com.example.todolist.exceptions.types.TodoListNotFoundException;
import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.items.dto.ItemInfoDto;
import com.example.todolist.todolists.dto.TodoListDto;
import com.example.todolist.todolists.dto.TodoListInfoDto;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.utils.response.ErrorResponseBody;
import com.example.todolist.utils.response.PageResponse;
import com.example.todolist.todolists.service.TodoListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/todo-lists", produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoListController {

    private final TodoListService todoListService;

    private final AuthService authService;

    @GetMapping
    @Operation(summary = "Get all the User's To-Do Lists paginated and sorted by Date of creation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User's To-Do Lists",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden type of User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<PageResponse> getTodoLists(
            @PageableDefault(size = 5) final Pageable paging,
            Authentication authentication) {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(todoListService.getAllTodoLists(userDto, paging), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get a To-Do List by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the specified To-Do List by its id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden type of User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "To-Do List not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<TodoListDto> getTodoListById(
            @PathVariable("id") final Integer id,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(todoListService.getTodoList(userDto, id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new To-Do List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the new To-Do List successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid To-Do List payload supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden type of User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500",
                    description = "Reached the maximum of To-Do Lists a User can have",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<TodoListDto> createTodoList(
            @Validated @RequestBody final TodoListInfoDto todoListInfoDto,
            final Authentication authentication) {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(
                todoListService.createTodoList(userDto, todoListInfoDto),
                HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update an existing To-Do List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated the existing To-Do List successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid To-Do List payload supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden type of User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "To-Do List not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<TodoListDto> updateTodoList(
            @PathVariable("id") final Integer id,
            @Validated @RequestBody final TodoListInfoDto todoListInfoDto,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(
                todoListService.updateTodoList(userDto, id, todoListInfoDto),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete an existing To-Do List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deleted the existing To-Do List successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden type of User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "To-Do List not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<Boolean> deleteTodoList(
            @PathVariable("id") final Integer id,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        todoListService.deleteTodoList(userDto, id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /* To-Do Items */

    @GetMapping(path = "/{id}/items")
    @Operation(summary = "Get all Items from a To-Do List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the To-Do List Items",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden type of User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<List<ItemDto>> getTodoListItems(
            @PathVariable("id") final Integer id,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(todoListService.getTodoListItems(userDto, id), HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/items")
    @Operation(summary = "Create a new Item for a To-Do List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the new Item successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Item payload supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden type of User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500",
                    description = "Reached the maximum of Items a To-Do List can have",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<ItemDto> addTodoListItem(
            @PathVariable("id") final Integer id,
            @Validated @RequestBody final ItemInfoDto itemInfoDto,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(
                todoListService.addTodoListItem(userDto, id, itemInfoDto),
                HttpStatus.CREATED);
    }

    @PutMapping(path = "/{todoListId}/items/{itemId}")
    @Operation(summary = "Update an existing To-Do List Item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Updated the Item successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Item payload supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden type of User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "To-Do List Item not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<ItemDto> updateTodoListItem(
            @PathVariable("todoListId") Integer todoListId,
            @PathVariable("itemId") Integer itemId,
            @Validated @RequestBody ItemInfoDto itemInfoDto,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(
                todoListService.updateTodoListItem(userDto, todoListId, itemId, itemInfoDto),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/{todoListId}/items/{itemId}")
    @Operation(summary = "Delete an existing Item from a To-Do List")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Deleted the existing Item successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden type of User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "404", description = "To-Do List Item not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseBody.class)) }) })
    public ResponseEntity<Boolean> deleteTodoListItem(
            @PathVariable("todoListId") Integer todoListId,
            @PathVariable("itemId") Integer itemId,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        todoListService.deleteTodoListItem(userDto, todoListId, itemId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
