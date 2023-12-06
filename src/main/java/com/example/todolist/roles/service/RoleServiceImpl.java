package com.example.todolist.roles.service;

import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.roles.dto.RoleInfoDto;
import com.example.todolist.roles.entity.Role;
import com.example.todolist.roles.mapper.RoleMapper;
import com.example.todolist.roles.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;
    
    public RoleDto getRole(Integer id) throws RoleNotFoundException {
        log.info("Getting Role with id: '{}' from the database", id);
        Role role = roleRepository
                .findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        log.info("Role with id: '{}' found", id);
        return roleMapper.convertToDto(role);
    }

    public RoleDto getRole(String name) throws RoleNotFoundException {
        log.info("Getting Role with name: '{}' from the database", name);
        Role role = roleRepository
                .findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(name));
        log.info("Role with name: '{}' found", name);
        return roleMapper.convertToDto(role);
    }

    public List<RoleDto> getAllRoles() {
        log.info("Getting all the Roles from the database");
        return roleMapper.convertNEntitiesToDto(roleRepository.findAll());
    }

    public RoleDto createRole(RoleInfoDto roleInfoDto) {
        log.info("Creating a new Role with name: '{}'", roleInfoDto.getName());
        Role role = roleMapper.convertToEntity(roleInfoDto);
        return roleMapper.convertToDto(roleRepository.save(role));
    }

    public RoleDto updateRole(Integer id, RoleInfoDto roleInfoDto) throws RoleNotFoundException {
        log.info("Updating Role with id: '{}'", id);
        Role role = roleRepository
                .findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        role.setName(roleInfoDto.getName());
        return roleMapper.convertToDto(roleRepository.save(role));
    }

    public void deleteRole(Integer id) throws RoleNotFoundException {
        Role role = roleRepository
                .findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        log.info("Deleting Role with id: '{}'", id);
        roleRepository.delete(role);
    }
    
}
