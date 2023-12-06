package com.example.todolist.auth.service;

import com.example.todolist.auth.dto.AuthDto;
import com.example.todolist.enums.UserRole;
import com.example.todolist.exceptions.types.DuplicateValueException;
import com.example.todolist.exceptions.types.RoleNotFoundException;
import com.example.todolist.roles.entity.Role;
import com.example.todolist.roles.mapper.RoleMapper;
import com.example.todolist.roles.repository.RoleRepository;
import com.example.todolist.users.entity.UserDetailsImpl;
import com.example.todolist.users.dto.UserDto;
import com.example.todolist.users.entity.User;
import com.example.todolist.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    public UserDto getCurrentUser(Authentication authentication) throws AuthenticationException {
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("Credentials not found. User is not Logged-In");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return UserDto
                .builder()
                .id(userDetails.getId().intValue())
                .email(userDetails.getEmail())
                .role(roleMapper.convertToDto(userDetails.getRole()))
                .build();
    }

    public boolean registerUser(AuthDto authDto) throws DuplicateValueException, RoleNotFoundException {
        if (userRepository.existsByEmail(authDto.getEmail())) {
            throw new DuplicateValueException("User", "email", authDto.getEmail());
        }
        Role role = roleRepository
                .findByName(UserRole.USER.name())
                .orElseThrow(() -> new RoleNotFoundException(UserRole.USER.toString()));
        User user = User.builder()
                .email(authDto.getEmail())
                .password(encoder.encode(authDto.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);
        return true;
    }

    public UserDto loginUser(AuthDto authDto) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.getEmail(),
                        authDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return getCurrentUser(authentication);
    }

    public boolean closeAccount(Authentication authentication) throws AuthenticationException {
        userRepository.deleteById(getCurrentUser(authentication).getId());
        return true;
    }

}
