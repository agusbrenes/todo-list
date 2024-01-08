package com.example.todolist.utils.factories;

import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.roles.entity.Role;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.users.entity.User;

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

    public static User generateUser(Integer id, String email) {
        Role role = RoleFactory.generateRole(1, "USER");
        return User
                .builder()
                .id(id.longValue())
                .email(email)
                .role(role)
                .build();
    }

}
