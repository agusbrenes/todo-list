package com.example.todolist.roles.service;

import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.roles.dto.RoleInfoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    RoleDto getRole(Integer id) throws RoleNotFoundException;

    RoleDto getRole(String name) throws RoleNotFoundException;

    List<RoleDto> getAllRoles();

    RoleDto createRole(RoleInfoDto roleInfoDto);

    RoleDto updateRole(Integer id, RoleInfoDto roleInfoDto) throws RoleNotFoundException;

    void deleteRole(Integer id) throws RoleNotFoundException;

}
