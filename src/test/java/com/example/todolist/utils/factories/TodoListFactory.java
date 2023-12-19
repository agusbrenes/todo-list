package com.example.todolist.utils.factories;

import com.example.todolist.todolists.dto.TodoListDto;
import com.example.todolist.users.dto.UserDto;

import java.time.LocalDateTime;

public class TodoListFactory {

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

}
