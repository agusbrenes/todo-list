package com.example.todolist.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthDto {

    private String email;

    private String password;

}
