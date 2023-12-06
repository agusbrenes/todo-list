package com.example.todolist.roles.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class RoleDto {

    private Integer id;

    @NonNull
    private String name;

}
