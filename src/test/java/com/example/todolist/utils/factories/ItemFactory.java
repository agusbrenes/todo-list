package com.example.todolist.utils.factories;

import com.example.todolist.enums.Status;
import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.items.dto.ItemInfoDto;
import com.example.todolist.items.entity.Item;

public class ItemFactory {

    public static ItemInfoDto generateItemInfoDto(String description) {
        return ItemInfoDto
                .builder()
                .status(Status.PENDING)
                .description(description)
                .build();
    }

    public static ItemDto generateItemDto(Integer id, String description) {
        return ItemDto
                .builder()
                .id(id)
                .status(Status.PENDING)
                .description(description)
                .build();
    }

    public static Item generateItem(Integer id, String description) {
        return Item
                .builder()
                .id(id)
                .description(description)
                .status(Status.PENDING)
                .build();
    }

}
