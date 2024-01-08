package com.example.todolist.roles.repository;

import com.example.todolist.roles.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@Slf4j
@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Create New Role")
    void testCreateRole() {
        log.info("TEST - Create new Role");

        Role role = Role
                .builder()
                .name("USER")
                .build();
        Role createdRole = entityManager.persistAndFlush(role);

        Assertions.assertThat(roleRepository.findById(createdRole.getId())).isPresent();
    }

    @Test
    @DisplayName("Find Role By Name")
    void testFindRoleByName() {
        log.info("TEST - Find Role by Name");

        Role role = Role
                .builder()
                .name("USER")
                .build();
        entityManager.persistAndFlush(role);

        Assertions.assertThat(roleRepository.findByName(role.getName())).isPresent();
    }

    @Test
    @DisplayName("Find All Roles")
    void testFindAllRoles() {
        log.info("TEST - Find all Roles");

        Role role = Role
                .builder()
                .name("USER")
                .build();
        entityManager.persistAndFlush(role);
        List<Role> roles = roleRepository.findAll();

        Assertions.assertThat(roles).hasSize(1);
    }

    @Test
    @DisplayName("Update Role")
    void testUpdateRoles() {
        log.info("TEST - Update Role");

        Role role = Role
                .builder()
                .name("USER")
                .build();
        Role createdRole = entityManager.persistAndFlush(role);
        Integer roleId = createdRole.getId();
        createdRole.setName("MAINTAINER");
        entityManager.persistAndFlush(createdRole);

        Assertions.assertThat(roleRepository.findById(roleId).get().getName())
                .isEqualTo("MAINTAINER");
    }

    @Test
    @DisplayName("Delete Role")
    void testDeleteRole() {
        log.info("TEST - Delete Role");

        Role role = Role
                .builder()
                .name("USER")
                .build();
        Role createdRole = entityManager.persistAndFlush(role);
        Integer roleId = createdRole.getId();

        Assertions.assertThat(roleRepository.findById(roleId)).isPresent();

        entityManager.remove(createdRole);

        Assertions.assertThat(roleRepository.findById(roleId)).isEmpty();
    }

}
