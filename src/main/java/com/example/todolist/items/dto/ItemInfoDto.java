package com.example.todolist.items.dto;

import com.example.todolist.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfoDto {

    private String description;

    private Status status;

}
