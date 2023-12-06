package com.example.todolist.todolists.mapper;

import com.example.todolist.todolists.dto.TodoListDto;
import com.example.todolist.todolists.dto.TodoListInfoDto;
import com.example.todolist.todolists.entity.TodoList;
import com.example.todolist.utils.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TodoListMapper extends Mapper<TodoList, TodoListDto> {

    private final ModelMapper modelMapper;

    @Override
    public TodoListDto convertToDto(TodoList todoList) {
        return modelMapper.map(todoList, TodoListDto.class);
    }

    @Override
    public TodoList convertToEntity(TodoListDto todoListDto) {
        return modelMapper.map(todoListDto, TodoList.class);
    }

    public TodoList convertToEntity(TodoListInfoDto todoListInfoDto) {
        return modelMapper.map(todoListInfoDto, TodoList.class);
    }

}
