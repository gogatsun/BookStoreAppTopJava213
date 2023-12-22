package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.entity.User;
import com.example.bookstoreapplication.form.UserRegistrationForm;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface UserService {

    boolean registration(UserRegistrationForm userRegistrationForm);
    Optional<User> findByLogin(String username);
}
