package com.example.todolist.todolists.repository;

import com.example.todolist.todolists.entity.TodoList;
import com.example.todolist.utils.factories.TodoListFactory;
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
class TodoListRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TodoListRepository todoListRepository;

    @Test
    @DisplayName("Create new To-Do List and Find By Id")
    void testCreateAndFindTodoListById() {
        log.info("TEST - Create new To-Do List and Find by Id");

        TodoList todoList = TodoListFactory.generateTodoList(null, "Test List", null);
        TodoList createdTodoList = entityManager.persistAndFlush(todoList);

        Assertions.assertThat(todoListRepository.findById(createdTodoList.getId())).isPresent();
    }

    @Test
    @DisplayName("Find All To-Do Lists")
    void testFindAllTodoLists() {
        log.info("TEST - Find all To-Do Lists");

        TodoList todoList = TodoListFactory.generateTodoList(null, "Test List", null);
        entityManager.persistAndFlush(todoList);
        List<TodoList> todoLists = todoListRepository.findAll();

        Assertions.assertThat(todoLists).hasSize(1);
    }

    @Test
    @DisplayName("Update To-Do List")
    void testUpdateTodoLists() {
        log.info("TEST - Update To-Do List");

        TodoList todoList = TodoListFactory.generateTodoList(null, "Test List", null);
        TodoList createdTodoList = entityManager.persistAndFlush(todoList);
        Integer todoListId = createdTodoList.getId();
        createdTodoList.setTitle("Modified List");
        entityManager.persistAndFlush(createdTodoList);

        Assertions.assertThat(todoListRepository.findById(todoListId).get().getTitle())
                .isEqualTo("Modified List");
    }

    @Test
    @DisplayName("Delete To-Do List")
    void testDeleteTodoList() {
        log.info("TEST - Delete To-Do List");

        TodoList todoList = TodoListFactory.generateTodoList(null, "Test List", null);
        TodoList createdTodoList = entityManager.persistAndFlush(todoList);
        Integer todoListId = createdTodoList.getId();

        Assertions.assertThat(todoListRepository.findById(todoListId)).isPresent();

        entityManager.remove(createdTodoList);

        Assertions.assertThat(todoListRepository.findById(todoListId)).isEmpty();
    }


}
