package com.example.todolist.todolists.controller;

import com.example.todolist.auth.service.AuthService;
import com.example.todolist.exceptions.types.TodoListNotFoundException;
import com.example.todolist.todolists.dto.TodoListDto;
import com.example.todolist.todolists.service.TodoListService;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.utils.factories.TodoListFactory;
import com.example.todolist.utils.factories.UserFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TodoListControllerTest {

    @MockBean
    private TodoListService todoListService;

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/todo-lists/1 - Found")
    void testGetTodoListByIdFound() throws Exception {
        log.info("Testing [GET /api/todo-lists/1 - Found] Endpoint");

        UserDto userDto = UserFactory.generateUserDto(1, "user@email.com");
        TodoListDto todoListDto = TodoListFactory.generateTodoListDto(1, "Test List", userDto);

        Mockito.doReturn(userDto)
                .when(authService)
                .getCurrentUser(Mockito.any());

        Mockito.doReturn(todoListDto)
                .when(todoListService)
                .getTodoList(userDto, 1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/todo-lists/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("GET /api/todo-lists/1 - Not Found")
    void testGetTodoListByIdNotFound() throws Exception {
        log.info("Testing [GET /api/todo-lists/1 - Not Found] Endpoint");

        UserDto userDto = UserFactory.generateUserDto(1, "user@email.com");

        Mockito.doReturn(userDto)
                .when(authService)
                .getCurrentUser(Mockito.any());

        Mockito.doThrow(TodoListNotFoundException.class)
                .when(todoListService)
                .getTodoList(userDto, 1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/todo-lists/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
