package com.example.todolist.items.repository;

import com.example.todolist.enums.Status;
import com.example.todolist.items.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@Slf4j
@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Create new Item and Find By Id")
    void testCreateAndFindItemById() {
        log.info("TEST - Create new Item and Find by Id");

        Item item = Item
                .builder()
                .description("Test Item")
                .status(Status.PENDING)
                .status(Status.PENDING)
                .build();
        Item createdItem = entityManager.persistAndFlush(item);

        Assertions.assertThat(itemRepository.findById(createdItem.getId())).isPresent();
    }

    @Test
    @DisplayName("Find All Items")
    void testFindAllItems() {
        log.info("TEST - Find all Items");

        Item item = Item
                .builder()
                .description("Test Item")
                .status(Status.PENDING)
                .build();
        entityManager.persistAndFlush(item);
        List<Item> items = itemRepository.findAll();

        Assertions.assertThat(items).hasSize(1);
    }

    @Test
    @DisplayName("Update Item")
    void testUpdateItems() {
        log.info("TEST - Update Item");

        Item item = Item
                .builder()
                .description("Test Item")
                .status(Status.PENDING)
                .build();
        Item createdItem = entityManager.persistAndFlush(item);
        Integer itemId = createdItem.getId();
        createdItem.setStatus(Status.COMPLETED);
        entityManager.persistAndFlush(createdItem);

        Assertions.assertThat(itemRepository.findById(itemId).get().getStatus())
                .isEqualTo(Status.COMPLETED);
    }

    @Test
    @DisplayName("Delete Item")
    void testDeleteItem() {
        log.info("TEST - Delete Item");

        Item item = Item
                .builder()
                .description("Test Item")
                .status(Status.PENDING)
                .build();
        Item createdItem = entityManager.persistAndFlush(item);
        Integer itemId = createdItem.getId();

        Assertions.assertThat(itemRepository.findById(itemId)).isPresent();

        entityManager.remove(createdItem);

        Assertions.assertThat(itemRepository.findById(itemId)).isEmpty();
    }

}
