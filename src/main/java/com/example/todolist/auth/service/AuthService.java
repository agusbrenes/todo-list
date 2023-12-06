package com.example.todolist.auth.service;

import com.example.todolist.auth.dto.AuthDto;
import com.example.todolist.exceptions.types.DuplicateValueException;
import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.users.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    UserDto getCurrentUser(Authentication authentication) throws AuthenticationException;

    boolean registerUser(AuthDto authDto) throws DuplicateValueException, RoleNotFoundException;

    UserDto loginUser(AuthDto authDto) throws AuthenticationException;

    boolean closeAccount(Authentication authentication) throws AuthenticationException;

}
