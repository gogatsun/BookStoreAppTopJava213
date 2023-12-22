package com.example.bookstoreapplication.config;

import com.example.bookstoreapplication.rdb.repositories.UserRepository;
import com.example.bookstoreapplication.rdb.security.UserDetailsRdbService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


/**
 *
 *
 * */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public final UserRepository userRepository;

    private final DataSource dataSource;

    public WebSecurityConfig(UserRepository userRepository, DataSource dataSource) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/new-author/**", "/update-author/**").hasRole("ADMIN")
                        .requestMatchers("/new-book/**", "/update-book/**", "/add-authors/**",
                                "/delete-author/*", "/add-genres/**").hasRole("ADMIN")
                        .requestMatchers("/order/**").hasRole("USER")
                        .requestMatchers("/new-publisher/**", "/update-publisher/*").hasRole("ADMIN")
                        .requestMatchers("/new-series/**", "/update-series/**").hasRole("ADMIN")
                        .requestMatchers("/", "/books/**", "/authors/**",
                                "/publishers/**", "/series/**", "webjars/**",
                                "/registration/**", "/img/**", "/css/**"). permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/", true).permitAll())
                .logout(customizer -> customizer.logoutSuccessUrl("/"));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsRdbService(userRepository);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }
}
