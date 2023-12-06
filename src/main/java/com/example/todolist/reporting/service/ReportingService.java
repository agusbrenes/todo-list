package com.example.todolist.reporting.service;

import com.example.todolist.utils.response.CountReport;
import com.example.todolist.utils.response.ResultsReport;
import org.springframework.stereotype.Service;

@Service
public interface ReportingService {

    ResultsReport getAllUsers();

    ResultsReport getAllTodoLists();

    CountReport getTotalItems();

}
