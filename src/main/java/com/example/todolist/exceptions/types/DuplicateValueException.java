package com.example.todolist.exceptions.types;

public class DuplicateValueException extends RuntimeException {

    public DuplicateValueException(String entityName, String attributeName, Object value) {
        super(String.format("%s already exists with [%s]: '%s'", entityName, attributeName, String.valueOf(value)));
    }

}

