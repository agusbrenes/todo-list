package com.example.todolist.todolists.mapper;

import com.example.todolist.todolists.dto.TodoListDto;
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
    public TodoListDto convertToDto(TodoList role) {
        return modelMapper.map(role, TodoListDto.class);
    }

    @Override
    public TodoList convertToEntity(TodoListDto roleDto) {
        return modelMapper.map(roleDto, TodoList.class);
    }

}
