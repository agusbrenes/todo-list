package com.example.todolist.items;

import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.items.dto.ItemInfoDto;
import com.example.todolist.items.entity.Item;
import com.example.todolist.items.mapper.ItemMapper;
import com.example.todolist.items.repository.ItemRepository;
import com.example.todolist.items.service.ItemServiceImpl;
import com.example.todolist.utils.factories.ItemFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemServiceTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Test
    @DisplayName("Create New Item - Success")
    void testCreateItemSuccessful() {
        log.info("Creating a new Item");
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

}
