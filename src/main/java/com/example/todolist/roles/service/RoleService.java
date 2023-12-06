package com.example.todolist.roles.service;

import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.roles.dto.RoleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    RoleDto getRole(Integer id) throws RoleNotFoundException;

    RoleDto getRole(String name) throws RoleNotFoundException;

    List<RoleDto> getAllRoles();

    RoleDto createRole(RoleDto roleDto);

    RoleDto updateRole(Integer id, RoleDto roleDto) throws RoleNotFoundException;

    void deleteRole(Integer id) throws RoleNotFoundException;

}
