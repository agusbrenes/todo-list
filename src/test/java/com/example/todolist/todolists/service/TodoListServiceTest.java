package com.example.todolist.todolists.service;

import com.example.todolist.todolists.dto.TodoListDto;
import com.example.todolist.todolists.dto.TodoListInfoDto;
import com.example.todolist.todolists.entity.TodoList;
import com.example.todolist.todolists.mapper.TodoListMapper;
import com.example.todolist.todolists.repository.TodoListRepository;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.users.entity.User;
import com.example.todolist.users.mapper.UserMapper;
import com.example.todolist.utils.factories.TodoListFactory;
import com.example.todolist.utils.factories.UserFactory;
import com.example.todolist.utils.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class TodoListServiceTest {

    @InjectMocks
    private TodoListServiceImpl todoListService;

    @Mock
    private TodoListRepository todoListRepository;

    @Mock
    private TodoListMapper todoListMapper;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("Find To-Do List By ID: 1")
    void testFindTodoListById() {
        log.info("TEST - Find To-Do List by ID: 1");

        UserDto userDto = UserFactory.generateUserDto(1, "user@email.com");
        User user = UserFactory.generateUser(1, "user@email.com");
        TodoList todoList = TodoListFactory.generateTodoList(1, "Test List", user);
        TodoListDto todoListDto = TodoListFactory.generateTodoListDto(1, "Test List", userDto);

        Mockito.doReturn(user)
                .when(userMapper)
                .convertToEntity(userDto);

        Mockito.doReturn(Optional.of(todoList))
                .when(todoListRepository)
                .findByCreatorAndId(user, 1);

        Mockito.doReturn(todoListDto)
                .when(todoListMapper)
                .convertToDto(todoList);

        TodoListDto foundTodoListDto = todoListService.getTodoList(userDto, 1);

        Assertions.assertAll(
                () -> Assertions.assertEquals(todoListDto, foundTodoListDto),
                () -> Mockito
                        .verify(todoListRepository, Mockito.times(1))
                        .findByCreatorAndId(user, 1)
        );
    }

    @Test
    @DisplayName("Find All To-Do Lists")
    void testFindAllTodoLists() {
        log.info("TEST - Find All To-Do Lists");

        Pageable paging = Pageable.ofSize(10);
        UserDto userDto = UserFactory.generateUserDto(1, "user@email.com");
        User user = UserFactory.generateUser(1, "user@email.com");
        TodoList todoList = TodoListFactory.generateTodoList(1, "Test List", user);
        TodoListDto todoListDto = TodoListFactory.generateTodoListDto(1, "Test List", userDto);
        Page<TodoList> todoListPage = new PageImpl<>(List.of(todoList));
        PageResponse todoListDtoPageResponse = PageResponse
                .builder()
                .results(List.of(todoListDto))
                .size(1)
                .totalPages(1)
                .totalResults(1)
                .build();

        Mockito.doReturn(user)
                .when(userMapper)
                .convertToEntity(userDto);

        Mockito.doReturn(todoListPage)
                .when(todoListRepository)
                .findAllByCreatorOrderByCreatedAtAsc(user, paging);

        Mockito.doReturn(List.of(todoListDto))
                .when(todoListMapper)
                .convertNEntitiesToDto(List.of(todoList));

        PageResponse todoListPageResponse = todoListService.getAllTodoLists(userDto, paging);

        Assertions.assertAll(
                () -> Assertions.assertEquals(todoListDtoPageResponse, todoListPageResponse),
                () -> Mockito
                        .verify(todoListRepository, Mockito.times(1))
                        .findAllByCreatorOrderByCreatedAtAsc(user, paging)
        );
    }

    @Test
    @DisplayName("Create New To-Do List")
    void testCreateTodoList() {
        log.info("TEST - Create New To-Do List");

        UserDto userDto = UserFactory.generateUserDto(1, "user@email.com");
        User user = UserFactory.generateUser(1, "user@email.com");
        TodoList todoList = TodoListFactory.generateTodoList(1, "Test List", user);
        TodoListDto todoListDto = TodoListFactory.generateTodoListDto(1, "Test List", userDto);
        TodoListInfoDto todoListInfoDto = TodoListFactory.generateTodoListInfoDto("Test List");

        Mockito.doReturn(user)
                .when(userMapper)
                .convertToEntity(userDto);

        Mockito.doReturn(todoList)
                .when(todoListMapper)
                .convertToEntity(todoListInfoDto);

        Mockito.doReturn(todoList)
                .when(todoListRepository)
                .save(Mockito.any(TodoList.class));

        Mockito.doReturn(todoListDto)
                .when(todoListMapper)
                .convertToDto(todoList);

        TodoListDto createdTodoListDto = todoListService.createTodoList(userDto, todoListInfoDto);

        Assertions.assertAll(
                () -> Assertions.assertEquals(todoListDto, createdTodoListDto),
                () -> Mockito
                        .verify(todoListRepository, Mockito.times(1))
                        .save(Mockito.any(TodoList.class)),
                () -> Mockito
                        .verify(todoListMapper, Mockito.times(1))
                        .convertToDto(Mockito.any(TodoList.class)),
                () -> Mockito
                        .verify(todoListMapper, Mockito.times(1))
                        .convertToEntity(Mockito.any(TodoListInfoDto.class))
        );
    }

    @Test
    @DisplayName("Update To-Do List with ID: 1")
    void testUpdateTodoList() {
        log.info("TEST - Update To-Do List with ID: 1");

        UserDto userDto = UserFactory.generateUserDto(1, "user@email.com");
        User user = UserFactory.generateUser(1, "user@email.com");
        TodoList todoList = TodoListFactory.generateTodoList(1, "Test List", user);
        TodoList updatedTodoList = TodoListFactory.generateTodoList(1, "MAINTAINER", user);
        TodoListDto todoListDto = TodoListFactory.generateTodoListDto(1, "MAINTAINER", userDto);
        TodoListInfoDto todoListInfoDto = TodoListFactory.generateTodoListInfoDto("MAINTAINER");

        Mockito.doReturn(user)
                .when(userMapper)
                .convertToEntity(userDto);

        Mockito.doReturn(Optional.of(todoList))
                .when(todoListRepository)
                .findByCreatorAndId(user, 1);

        Mockito.doReturn(updatedTodoList)
                .when(todoListRepository)
                .save(todoList);

        Mockito.doReturn(todoListDto)
                .when(todoListMapper)
                .convertToDto(updatedTodoList);

        TodoListDto updatedTodoListDto = todoListService.updateTodoList(userDto, 1, todoListInfoDto);

        Assertions.assertAll(
                () -> Assertions.assertEquals(todoListDto, updatedTodoListDto),
                () -> Mockito
                        .verify(todoListRepository, Mockito.times(1))
                        .findByCreatorAndId(user, 1),
                () -> Mockito
                        .verify(todoListRepository, Mockito.times(1))
                        .save(Mockito.any(TodoList.class)),
                () -> Mockito
                        .verify(todoListMapper, Mockito.times(1))
                        .convertToDto(Mockito.any(TodoList.class))
        );
    }

    @Test
    @DisplayName("Delete To-Do List By ID: 1")
    void testDeleteTodoListById() {
        log.info("TEST - Delete To-Do List by ID: 1");

        UserDto userDto = UserFactory.generateUserDto(1, "user@email.com");
        User user = UserFactory.generateUser(1, "user@email.com");
        TodoList todoList = TodoListFactory.generateTodoList(1, "Test List", user);

        Mockito.doReturn(user)
                .when(userMapper)
                .convertToEntity(userDto);

        Mockito.doReturn(Optional.of(todoList))
                .when(todoListRepository)
                .findByCreatorAndId(user, 1);

        Mockito.doNothing()
                .when(todoListRepository)
                .delete(todoList);

        todoListService.deleteTodoList(userDto, 1);

        Assertions.assertAll(
                () -> Mockito
                        .verify(todoListRepository, Mockito.times(1))
                        .findByCreatorAndId(user, 1),
                () -> Mockito
                        .verify(todoListRepository, Mockito.times(1))
                        .delete(todoList)
        );
    }

}
