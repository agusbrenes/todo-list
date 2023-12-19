package com.example.todolist.roles.dto;

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
public class RoleInfoDto {

    @NotNull(message = "Role \"name\" cannot be null")
    @NotBlank(message = "Role \"name\" cannot be an empty")
    private String name;

}
