package com.example.todolist.todolists.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoListInfoDto {

    @NotNull(message = "To-Do List \"title\" cannot be null")
    @NotBlank(message = "To-Do List \"title\" cannot be an empty")
    private String title;

}
