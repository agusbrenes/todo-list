package com.example.todolist.todolists.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class TodoListInfoDto {

    @NonNull
    private String title;

}
