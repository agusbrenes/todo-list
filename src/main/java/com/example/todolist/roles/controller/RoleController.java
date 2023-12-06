package com.example.todolist.roles.controller;

import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.roles.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> getRoleById(@PathVariable("id") Integer id)
            throws RoleNotFoundException {
        return new ResponseEntity<>(roleService.getRole(id), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> createRole(@Validated @RequestBody RoleDto roleDto) {
        return new ResponseEntity<>(roleService.createRole(roleDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> updateRole(
            @PathVariable("id") Integer id,
            @Validated @RequestBody RoleDto roleDto) throws RoleNotFoundException {
        return new ResponseEntity<>(roleService.updateRole(id, roleDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRole(@PathVariable("id") Integer id)
            throws RoleNotFoundException {
        roleService.deleteRole(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
