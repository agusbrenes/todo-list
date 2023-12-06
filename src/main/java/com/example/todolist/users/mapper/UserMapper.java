package com.example.todolist.users.mapper;

import com.example.todolist.users.dto.UserDto;
import com.example.todolist.users.entity.User;
import com.example.todolist.utils.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper extends Mapper<User, UserDto> {

    private final ModelMapper modelMapper;

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

}

