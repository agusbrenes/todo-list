package com.example.todolist.utils.factories;

import com.example.todolist.todolists.dto.TodoListDto;
import com.example.todolist.todolists.dto.TodoListInfoDto;
import com.example.todolist.todolists.entity.TodoList;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.users.entity.User;

import java.time.LocalDateTime;

public class TodoListFactory {

    public static TodoListInfoDto generateTodoListInfoDto(String title) {
        return TodoListInfoDto
                .builder()
                .title(title)
                .build();
    }

    public static TodoListDto generateTodoListDto(Integer id, String title, UserDto creator) {
        return TodoListDto
                .builder()
                .id(id)
                .title(title)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .creator(creator)
                .build();
    }

    public static TodoList generateTodoList(Integer id, String title, User creator) {
        return TodoList
                .builder()
                .id(id)
                .title(title)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .creator(creator)
                .build();
    }

}
