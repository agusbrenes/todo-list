package com.example.todolist.todolists.service;

import com.example.todolist.exceptions.types.ItemNotFoundException;
import com.example.todolist.exceptions.types.LimitException;
import com.example.todolist.exceptions.types.TodoListNotFoundException;
import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.items.dto.ItemInfoDto;
import com.example.todolist.todolists.dto.TodoListDto;
import com.example.todolist.todolists.dto.TodoListInfoDto;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.utils.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoListService {

    TodoListDto getTodoList(UserDto userDto, Integer id) throws TodoListNotFoundException;

    PageResponse getAllTodoLists(UserDto userDto, Pageable paging);

    TodoListDto createTodoList(UserDto userDto, TodoListInfoDto todoListInfoDto)
            throws LimitException;

    TodoListDto updateTodoList(UserDto userDto, Integer id, TodoListInfoDto todoListInfoDto)
            throws TodoListNotFoundException;

    void deleteTodoList(UserDto userDto, Integer id) throws TodoListNotFoundException;

    /* To-Do List Items */

    List<ItemDto> getTodoListItems(UserDto userDto, Integer id) throws TodoListNotFoundException;

    ItemDto addTodoListItem(UserDto userDto, Integer id, ItemInfoDto itemInfoDto)
            throws TodoListNotFoundException, LimitException;

    ItemDto updateTodoListItem(UserDto userDto,
                               Integer todoListId,
                               Integer itemId,
                               ItemInfoDto itemInfoDto)
            throws TodoListNotFoundException, ItemNotFoundException;

    void deleteTodoListItem(UserDto userDto, Integer todoListId, Integer itemId)
            throws TodoListNotFoundException, ItemNotFoundException;

}
