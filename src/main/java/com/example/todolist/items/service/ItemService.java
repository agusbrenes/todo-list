package com.example.todolist.items.service;

import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.exceptions.types.ItemNotFoundException;
import com.example.todolist.todolists.entity.TodoList;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    ItemDto createItem(ItemDto itemDto);

    ItemDto updateItem(TodoList todoList, Integer itemId, ItemDto itemDto)
            throws ItemNotFoundException;

    void deleteItem(TodoList todoList, Integer itemId) throws ItemNotFoundException;

}
