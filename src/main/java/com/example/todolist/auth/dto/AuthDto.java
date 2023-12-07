package com.example.todolist.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthDto {

    @NotNull(message = "User \"email\" cannot be null")
    @NotBlank(message = "User \"email\" cannot be an empty")
    private String email;

    @NotNull(message = "User \"password\" cannot be null")
    @NotBlank(message = "User \"password\" cannot be an empty")
    private String password;

}
