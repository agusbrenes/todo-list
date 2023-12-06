package com.example.todolist.todolists.controller;

import com.example.todolist.auth.service.AuthService;
import com.example.todolist.exceptions.types.TodoListNotFoundException;
import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.items.dto.ItemInfoDto;
import com.example.todolist.todolists.dto.TodoListDto;
import com.example.todolist.todolists.dto.TodoListInfoDto;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.utils.response.PageResponse;
import com.example.todolist.todolists.service.TodoListService;
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
    public ResponseEntity<PageResponse> getTodoLists(
            @PageableDefault(size = 5) final Pageable paging,
            Authentication authentication) {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(todoListService.getAllTodoLists(userDto, paging), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TodoListDto> getTodoListById(
            @PathVariable("id") final Integer id,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(todoListService.getTodoList(userDto, id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TodoListDto> createTodoList(
            @Validated @RequestBody final TodoListInfoDto todoListInfoDto,
            final Authentication authentication) {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(
                todoListService.createTodoList(userDto, todoListInfoDto),
                HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TodoListDto> updateTodoList(
            @PathVariable("id") final Integer id,
            @Validated @RequestBody final TodoListInfoDto todoListInfoDto,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(
                todoListService.updateTodoList(userDto, id, todoListInfoDto),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTodoList(
            @PathVariable("id") final Integer id,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        todoListService.deleteTodoList(userDto, id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /* To-Do Items */

    @GetMapping(path = "/{id}/items")
    public ResponseEntity<List<ItemDto>> getTodoListItems(
            @PathVariable("id") final Integer id,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        return new ResponseEntity<>(todoListService.getTodoListItems(userDto, id), HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/items")
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
    public ResponseEntity<Boolean> deleteTodoListItem(
            @PathVariable("todoListId") Integer todoListId,
            @PathVariable("itemId") Integer itemId,
            Authentication authentication) throws TodoListNotFoundException {
        UserDto userDto = authService.getCurrentUser(authentication);
        todoListService.deleteTodoListItem(userDto, todoListId, itemId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
