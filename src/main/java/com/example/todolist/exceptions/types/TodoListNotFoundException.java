package com.example.todolist.exceptions.types;

public class TodoListNotFoundException extends NotFoundException {

    public TodoListNotFoundException(Integer id) {
        super("To-Do List", "id", id);
    }

}
