package com.example.todolist.reporting.controller;

import com.example.todolist.reporting.service.ReportingService;
import com.example.todolist.utils.response.CountReport;
import com.example.todolist.utils.response.ResultsReport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/reporting", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping(path = "/todo-lists")
    public ResponseEntity<ResultsReport> getAllTodoLists() {
        return new ResponseEntity<>(reportingService.getAllTodoLists(), HttpStatus.OK);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<ResultsReport> getAllUsers() {
        return new ResponseEntity<>(reportingService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/todo-lists/items")
    public ResponseEntity<CountReport> getTotalItems() {
        return new ResponseEntity<>(reportingService.getTotalItems(), HttpStatus.OK);
    }

}
