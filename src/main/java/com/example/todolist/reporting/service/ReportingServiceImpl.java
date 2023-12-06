package com.example.todolist.reporting.service;

import com.example.todolist.items.repository.ItemRepository;
import com.example.todolist.todolists.entity.TodoList;
import com.example.todolist.todolists.repository.TodoListRepository;
import com.example.todolist.users.entity.User;
import com.example.todolist.users.repository.UserRepository;
import com.example.todolist.utils.response.CountReport;
import com.example.todolist.utils.response.ResultsReport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    private final TodoListRepository todoListRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    public ResultsReport getAllUsers() {
        List<String> users = userRepository
                .findAll()
                .stream()
                .map(User::getEmail)
                .toList();
        return ResultsReport
                .builder()
                .results(users)
                .totalResults(users.size())
                .build();
    }

    public ResultsReport getAllTodoLists() {
        List<String> todoLists = todoListRepository
                .findAll()
                .stream()
                .map(TodoList::getTitle)
                .toList();
        return ResultsReport
                .builder()
                .results(todoLists)
                .totalResults(todoLists.size())
                .build();
    }

    public CountReport getTotalItems() {
        return CountReport
                .builder()
                .totalResults(itemRepository.count())
                .build();
    }
}
