package com.example.todolist.roles.mapper;

import com.example.todolist.roles.entity.Role;
import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.utils.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleMapper extends Mapper<Role, RoleDto> {

    private final ModelMapper modelMapper;

    @Override
    public RoleDto convertToDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public Role convertToEntity(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

}
