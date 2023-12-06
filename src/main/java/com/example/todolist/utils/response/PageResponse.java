package com.example.todolist.utils.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResponse {

    private int page;

    private int size;

    private int totalPages;

    private long totalResults;

    private List<?> results;

}
