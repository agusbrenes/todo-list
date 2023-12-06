package com.example.todolist.config.security;

import com.example.todolist.enums.UserRole;
import com.example.todolist.exceptions.handlers.AuthorizationExceptionHandler;
import com.example.todolist.users.service.UserDetailsServiceImpl;
import com.example.todolist.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private static final String BASE_URL = "/api";

    private static final String PATH_MATCHER = "/**";

    private static final String AUTH_URL = BASE_URL + "/auth" + PATH_MATCHER;

    private static final String TODO_LISTS_URL = BASE_URL + "/todo-lists" + PATH_MATCHER;

    private static final String ROLES_URL = BASE_URL + "/roles" + PATH_MATCHER;

    private static final String REPORTING_URL = BASE_URL + "/reporting" + PATH_MATCHER;

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthenticationEntryPoint unauthorizedHandler;

    private final JwtUtils jwtUtils;

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AuthorizationExceptionHandler();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(accessDeniedHandler()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                            .requestMatchers(AUTH_URL)
                            .permitAll()
                            .requestMatchers(TODO_LISTS_URL)
                            .hasAuthority(UserRole.USER.name())
                            .requestMatchers(REPORTING_URL, ROLES_URL)
                            .hasAuthority(UserRole.MAINTAINER.name())
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
