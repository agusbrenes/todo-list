package com.example.todolist.utils.factories;

import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.users.dto.UserDto;

public final class UserFactory {

    public static UserDto generateUserDto(Integer id, String email) {
        RoleDto roleDto = RoleFactory.generateRoleDto(1, "USER");
        return UserDto
                .builder()
                .id(id)
                .email(email)
                .role(roleDto)
                .build();
    }

    public static UserDto generateMaintainerUserDto(Integer id, String email) {
        RoleDto roleDto = RoleFactory.generateRoleDto(1, "MAINTAINER");
        return UserDto
                .builder()
                .id(id)
                .email(email)
                .role(roleDto)
                .build();
    }

}
