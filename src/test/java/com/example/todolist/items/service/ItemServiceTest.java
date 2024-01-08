package com.example.todolist.items.service;

import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.items.dto.ItemInfoDto;
import com.example.todolist.items.entity.Item;
import com.example.todolist.items.mapper.ItemMapper;
import com.example.todolist.items.repository.ItemRepository;
import com.example.todolist.utils.factories.ItemFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
class ItemServiceTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Test
    @DisplayName("Create New Item")
    void testCreateItemSuccessful() {
        log.info("TEST - Create new Item");
        ItemInfoDto mockItemInfoDto = ItemFactory.generateItemInfoDto("Test Item");
        ItemDto mockItemDto = ItemFactory.generateItemDto(1, "Test Item");
        Item mockItem = ItemFactory.generateItem(1, "Test Item");

        Mockito.doReturn(mockItem)
                .when(itemMapper)
                .convertToEntity(Mockito.any(ItemInfoDto.class));

        Mockito.doReturn(mockItem)
                .when(itemRepository)
                .save(Mockito.any(Item.class));

        Mockito.doReturn(mockItemDto)
                .when(itemMapper)
                .convertToDto(Mockito.any(Item.class));

        ItemDto createdItem = itemService.createItem(mockItemInfoDto);

        Assertions.assertAll(
                () -> Assertions.assertEquals(mockItemDto, createdItem),
                () -> Mockito
                        .verify(itemRepository, Mockito.times(1))
                        .save(Mockito.any(Item.class))
        );
    }

    @Test
    @DisplayName("Update Item with ID: 1")
    void testUpdateItem() {
        log.info("TEST - Update Item with ID: 1");
        Item item = ItemFactory.generateItem(1, "Test Item");
        Item updatedItem = ItemFactory.generateItem(1, "Test Item #2");
        ItemDto itemDto = ItemFactory.generateItemDto(1, "Test Item #2");
        ItemInfoDto itemInfoDto = ItemFactory.generateItemInfoDto("Test Item #2");

        Mockito.doReturn(Optional.of(item))
                .when(itemRepository)
                .findById(1);

        Mockito.doReturn(updatedItem)
                .when(itemRepository)
                .save(Mockito.any(Item.class));

        Mockito.doReturn(itemDto)
                .when(itemMapper)
                .convertToDto(item);

        // TODO FIX
        ItemDto updatedItemDto = itemService.updateItem(null, 1, itemInfoDto);

        Assertions.assertAll(
                () -> Assertions.assertEquals(itemDto, updatedItemDto),
                () -> Mockito
                        .verify(itemRepository, Mockito.times(1))
                        .findById(Mockito.anyInt()),
                () -> Mockito
                        .verify(itemRepository, Mockito.times(1))
                        .save(Mockito.any(Item.class)),
                () -> Mockito
                        .verify(itemMapper, Mockito.times(1))
                        .convertToDto(Mockito.any(Item.class))
        );
    }

    @Test
    @DisplayName("Delete Item By ID: 1")
    void testDeleteItemById() {
        log.info("TEST - Delete Item by ID: 1");
        Item item = ItemFactory.generateItem(1, "Test Item");

        Mockito.doReturn(Optional.of(item))
                .when(itemRepository)
                .findById(1);

        Mockito.doNothing()
                .when(itemRepository)
                .delete(item);

        // TODO FIX
        itemService.deleteItem(null, 1);

        Assertions.assertAll(
                () -> Mockito
                        .verify(itemRepository, Mockito.times(1))
                        .findById(1),
                () -> Mockito
                        .verify(itemRepository, Mockito.times(1))
                        .delete(item)
        );
    }

}
