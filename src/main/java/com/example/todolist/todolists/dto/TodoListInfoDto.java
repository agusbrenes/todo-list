package com.example.todolist.todolists.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoListInfoDto {

    @NotNull(message = "To-Do List \"title\" cannot be null")
    @NotBlank(message = "To-Do List \"title\" cannot be an empty")
    private String title;

}
