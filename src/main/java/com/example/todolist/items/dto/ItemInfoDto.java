package com.example.todolist.items.dto;

import com.example.todolist.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemInfoDto {

    private String description;

    private Status status;

}
