package com.example.todolist.exceptions.types;

import com.example.todolist.utils.resources.Resource;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String entityName, String attributeName, Object value) {
        super(String.format("%s not found with [%s]: '%s'", entityName, attributeName, String.valueOf(value)));
    }

    public NotFoundException(String entityName, String attributeName) {
        super(String.format("%s not found with [%s]", entityName, attributeName));
    }

    public NotFoundException(Resource resource) {
        super(String.format("%s not found with %s", resource.getName(), resource.getFieldsString()));
    }

}

