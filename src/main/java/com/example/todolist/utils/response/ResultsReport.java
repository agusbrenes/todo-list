package com.example.todolist.utils.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResultsReport {

    private long totalResults;

    private List<?> results;

}
