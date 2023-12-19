package com.example.todolist.roles.service;

import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.roles.dto.RoleInfoDto;
import com.example.todolist.roles.entity.Role;
import com.example.todolist.roles.mapper.RoleMapper;
import com.example.todolist.roles.repository.RoleRepository;
import com.example.todolist.utils.factories.RoleFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class RoleServiceTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @Test
    @DisplayName("Find Role By ID: 1")
    void testFindRoleById() {
        log.info("TEST - Find Role by ID: 1");
        Role role = RoleFactory.generateRole(1, "USER");
        RoleDto roleDto = RoleFactory.generateRoleDto(1, "USER");

        Mockito.doReturn(Optional.of(role))
                .when(roleRepository)
                .findById(1);

        Mockito.doReturn(roleDto)
                .when(roleMapper)
                .convertToDto(role);

        RoleDto foundRoleDto = roleService.getRole(1);

        Assertions.assertAll(
                () -> Assertions.assertEquals(roleDto, foundRoleDto),
                () -> Mockito
                        .verify(roleRepository, Mockito.times(1))
                        .findById(1)
        );
    }

    @Test
    @DisplayName("Create New Role")
    void testCreateRole() {
        log.info("TEST - Create New Role");
        Role role = RoleFactory.generateRole(1, "USER");
        RoleDto roleDto = RoleFactory.generateRoleDto(1, "USER");
        RoleInfoDto roleInfoDto = RoleFactory.generateRoleInfoDto("USER");

        Mockito.doReturn(role)
                .when(roleMapper)
                .convertToEntity(roleInfoDto);

        Mockito.doReturn(role)
                .when(roleRepository)
                .save(Mockito.any(Role.class));

        Mockito.doReturn(roleDto)
                .when(roleMapper)
                .convertToDto(role);

        RoleDto createdRoleDto = roleService.createRole(roleInfoDto);

        Assertions.assertAll(
                () -> Assertions.assertEquals(roleDto, createdRoleDto),
                () -> Mockito
                        .verify(roleRepository, Mockito.times(1))
                        .save(Mockito.any(Role.class)),
                () -> Mockito
                        .verify(roleMapper, Mockito.times(1))
                        .convertToDto(Mockito.any(Role.class)),
                () -> Mockito
                        .verify(roleMapper, Mockito.times(1))
                        .convertToEntity(Mockito.any(RoleInfoDto.class))
        );
    }

    @Test
    @DisplayName("Update Role with ID: 1")
    void testUpdateRole() {
        log.info("TEST - Update Role with ID: 1");
        Role role = RoleFactory.generateRole(1, "USER");
        Role updatedRole = RoleFactory.generateRole(1, "MAINTAINER");
        RoleDto roleDto = RoleFactory.generateRoleDto(1, "MAINTAINER");
        RoleInfoDto roleInfoDto = RoleFactory.generateRoleInfoDto("MAINTAINER");

        Mockito.doReturn(Optional.of(role))
                .when(roleRepository)
                .findById(1);

        Mockito.doReturn(updatedRole)
                .when(roleRepository)
                .save(Mockito.any(Role.class));

        Mockito.doReturn(roleDto)
                .when(roleMapper)
                .convertToDto(role);

        RoleDto updatedRoleDto = roleService.updateRole(1, roleInfoDto);

        Assertions.assertAll(
                () -> Assertions.assertEquals(roleDto, updatedRoleDto),
                () -> Mockito
                        .verify(roleRepository, Mockito.times(1))
                        .findById(Mockito.anyInt()),
                () -> Mockito
                        .verify(roleRepository, Mockito.times(1))
                        .save(Mockito.any(Role.class)),
                () -> Mockito
                        .verify(roleMapper, Mockito.times(1))
                        .convertToDto(Mockito.any(Role.class))
        );
    }

    @Test
    @DisplayName("Find All Roles")
    void testFindAllRoles() {
        log.info("TEST - Find All Roles");
        Role role = RoleFactory.generateRole(1, "USER");
        RoleDto roleDto = RoleFactory.generateRoleDto(1, "USER");

        Mockito.doReturn(List.of(role))
                .when(roleRepository)
                .findAll();

        Mockito.doReturn(List.of(roleDto))
                .when(roleMapper)
                .convertNEntitiesToDto(List.of(role));

        List<RoleDto> roleDtoList = roleService.getAllRoles();

        Assertions.assertAll(
                () -> Assertions.assertEquals(List.of(roleDto), roleDtoList),
                () -> Mockito
                        .verify(roleRepository, Mockito.times(1))
                        .findAll()
        );
    }

    @Test
    @DisplayName("Delete Role By ID: 1")
    void testDeleteRoleById() {
        log.info("TEST - Delete Role by ID: 1");
        Role role = RoleFactory.generateRole(1, "USER");

        Mockito.doReturn(Optional.of(role))
                .when(roleRepository)
                .findById(1);

        Mockito.doNothing()
                .when(roleRepository)
                .delete(role);

        roleService.deleteRole(1);

        Assertions.assertAll(
                () -> Mockito
                        .verify(roleRepository, Mockito.times(1))
                        .findById(1),
                () -> Mockito
                        .verify(roleRepository, Mockito.times(1))
                        .delete(role)
        );
    }

}
