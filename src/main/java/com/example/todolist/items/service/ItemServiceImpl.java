package com.example.todolist.items.service;

import com.example.todolist.enums.Status;
import com.example.todolist.exceptions.types.ItemNotFoundException;
import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.items.mapper.ItemMapper;
import com.example.todolist.items.repository.ItemRepository;
import com.example.todolist.items.entity.Item;
import com.example.todolist.todolists.entity.TodoList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    public ItemDto createItem(ItemDto itemDto) {
        log.info("Creating a new Item with description: '{}'", itemDto.getDescription());
        Item item = itemMapper.convertToEntity(itemDto);
        item.setStatus(Status.PENDING);
        return itemMapper.convertToDto(itemRepository.save(item));
    }

    public ItemDto updateItem(TodoList todoList, Integer itemId, ItemDto itemDto)
            throws ItemNotFoundException {
        log.info("Getting Item with id: '{}' from To-Do List with id: '{}'",
                itemId,
                todoList.getId());
        Item item = itemRepository
                .findByTodoListAndId(todoList, itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId, todoList.getId()));
        log.info("Item with id: '{}' from To-Do List with id: '{}' found",
                itemId,
                todoList.getId());
        log.info("Updating Item with id: '{}', description: '{}' and status: '{}'",
                itemId,
                itemDto.getDescription(),
                itemDto.getStatus());
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getStatus() != null) {
            item.setStatus(itemDto.getStatus());
        }
        return itemMapper.convertToDto(itemRepository.save(item));
    }

    public void deleteItem(TodoList todoList, Integer itemId) throws ItemNotFoundException {
        log.info("Getting Item with id: '{}' from To-Do List with id: '{}'",
                itemId,
                todoList.getId());
        Item item = itemRepository
                .findByTodoListAndId(todoList, itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId, todoList.getId()));
        log.info("Deleting Item with id: '{}'", itemId);
        itemRepository.delete(item);
    }

}
