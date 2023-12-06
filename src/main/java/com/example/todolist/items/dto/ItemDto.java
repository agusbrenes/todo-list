package com.example.todolist.items.dto;

import com.example.todolist.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {

    private Integer id;

    private String description;

    private Status status;

}
