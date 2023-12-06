package com.example.todolist.exceptions.types;

import com.example.todolist.utils.resources.Field;
import com.example.todolist.utils.resources.Resource;

import java.util.List;

public class ItemNotFoundException extends NotFoundException {

    public ItemNotFoundException(Integer itemId, Integer todoListId) {
        super(Resource.builder()
                .name("Item")
                .fields(List.of(
                        Field.builder()
                                .name("id")
                                .value(itemId)
                                .build(),
                        Field.builder()
                                .name("To-Do List id")
                                .value(todoListId)
                                .build()
                ))
                .build());
    }

}
