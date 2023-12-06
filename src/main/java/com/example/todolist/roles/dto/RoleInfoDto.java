package com.example.todolist.roles.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class RoleInfoDto {

    @NonNull
    private String name;

}
