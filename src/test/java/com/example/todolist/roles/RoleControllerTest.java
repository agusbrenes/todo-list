package com.example.todolist.roles;

import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.roles.dto.RoleDto;
import com.example.todolist.roles.dto.RoleInfoDto;
import com.example.todolist.roles.service.RoleServiceImpl;
import com.example.todolist.utils.factories.RoleFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.todolist.utils.TestUtils.convertObjToJsonString;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RoleControllerTest {

    @MockBean
    private RoleServiceImpl roleService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/roles/1 - Found")
    void testGetRoleByIdFound() throws Exception {
        log.info("Testing [GET /api/roles/1 - Found] Endpoint");

        RoleDto roleDto = RoleFactory.generateRoleDto(1, "USER");

        Mockito.doReturn(roleDto)
                .when(roleService)
                .getRole(1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/roles/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(convertObjToJsonString(roleDto)));
    }

    @Test
    @DisplayName("GET /api/roles/1 - Not Found")
    void testGetRoleByIdNotFound() throws Exception {
        log.info("Testing [GET /api/roles/1 - Not Found] Endpoint");

        Mockito.doThrow(RoleNotFoundException.class)
                .when(roleService)
                .getRole(1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/roles/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/roles - Created")
    void testCreateRoleSuccess() throws Exception {
        log.info("Testing [POST /api/roles - Created] Endpoint");

        RoleDto roleDto = RoleFactory.generateRoleDto(1, "USER");
        RoleInfoDto roleInfoDto = RoleFactory.generateRoleInfoDto("USER");

        Mockito.doReturn(roleDto)
                .when(roleService)
                .createRole(roleInfoDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjToJsonString(roleInfoDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(convertObjToJsonString(roleDto)));
    }

    @Test
    @DisplayName("PUT /api/roles/1 - Updated")
    void testUpdateRoleSuccess() throws Exception {
        log.info("Testing [PUT /api/roles/1 - Updated] Endpoint");

        RoleDto roleDto = RoleFactory.generateRoleDto(1, "MAINTAINER");
        RoleInfoDto roleInfoDto = RoleFactory.generateRoleInfoDto("MAINTAINER");

        Mockito.doReturn(roleDto)
                .when(roleService)
                .updateRole(1, roleInfoDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/roles/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjToJsonString(roleInfoDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(convertObjToJsonString(roleDto)));
    }

    @Test
    @DisplayName("PUT /api/roles/1 - Not Found")
    void testUpdateRoleFail() throws Exception {
        log.info("Testing [PUT /api/roles/1 - Not Found] Endpoint");

        RoleInfoDto roleInfoDto = RoleFactory.generateRoleInfoDto("REGULAR");

        Mockito.doThrow(RoleNotFoundException.class)
                .when(roleService)
                .updateRole(1, roleInfoDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/roles/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjToJsonString(roleInfoDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/roles/1 - Deleted")
    void testDeleteRoleSuccess() throws Exception {
        log.info("Testing [DELETE /api/roles/1 - Deleted] Endpoint");

        Mockito.doNothing()
                .when(roleService)
                .deleteRole(1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/roles/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/roles/1 - Not Found")
    void testDeleteRoleFail() throws Exception {
        log.info("Testing [DELETE /api/roles/1 - Not Found] Endpoint");

        Mockito.doThrow(RoleNotFoundException.class)
                .when(roleService)
                .deleteRole(1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/roles/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
