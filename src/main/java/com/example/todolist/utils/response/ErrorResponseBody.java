package com.example.todolist.utils.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResponseBody {

    private int status;

    private HttpStatus error;

    private String message;

    private String path;

}
