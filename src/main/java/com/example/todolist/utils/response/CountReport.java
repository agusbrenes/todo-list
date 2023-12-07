package com.example.todolist.utils.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountReport {

    private long totalResults;

}
