package com.example.todolist.exceptions.types;

public class LimitException extends RuntimeException {

    public LimitException(String childEntities, String parentEntity, int maxAmount) {
        super(String.format("The maximum number of %s a %s can have is %s",
                childEntities,
                parentEntity,
                maxAmount));
    }

}
