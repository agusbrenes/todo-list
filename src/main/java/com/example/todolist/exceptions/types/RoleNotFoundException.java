package com.example.todolist.exceptions.types;

public class RoleNotFoundException extends NotFoundException {

    public RoleNotFoundException(Integer id) {
        super("Role", "id", id);
    }

    public RoleNotFoundException(String name) {
        super("Role", "name", name);
    }

}
