package com.example.todolist.items.mapper;

import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.items.dto.ItemInfoDto;
import com.example.todolist.items.entity.Item;
import com.example.todolist.utils.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ItemMapper extends Mapper<Item, ItemDto> {

    ModelMapper modelMapper;

    @Override
    public ItemDto convertToDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    @Override
    public Item convertToEntity(ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }

    public Item convertToEntity(ItemInfoDto itemInfoDto) {
        return modelMapper.map(itemInfoDto, Item.class);
    }

}
