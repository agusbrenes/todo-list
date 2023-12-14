package com.example.todolist.todolists.dto;

import com.example.todolist.items.dto.ItemDto;
import com.example.todolist.users.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoListDto {

    private Integer id;

    @NonNull
    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDto creator;

    private List<ItemDto> items;

}
