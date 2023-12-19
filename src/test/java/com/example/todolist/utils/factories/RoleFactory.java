package com.example.todolist.utils.factories;

import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.roles.dto.RoleInfoDto;
import com.example.todolist.roles.entity.Role;

public class RoleFactory {

    public static RoleDto generateRoleDto(Integer id, String name) {
        return RoleDto
                .builder()
                .id(id)
                .name(name)
                .build();
    }

    public static RoleInfoDto generateRoleInfoDto(String name) {
        return RoleInfoDto
                .builder()
                .name(name)
                .build();
    }

    public static Role generateRole(Integer id, String name) {
        return Role
                .builder()
                .id(id)
                .name(name)
                .build();
    }

}
