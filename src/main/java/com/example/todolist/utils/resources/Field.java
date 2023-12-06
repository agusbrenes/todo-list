package com.example.todolist.utils.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Field {

    private String name;

    private Object value;

}