package com.example.bookstoreapplication.rdb.service;

import com.example.bookstoreapplication.entity.Client;
import com.example.bookstoreapplication.entity.User;
import com.example.bookstoreapplication.form.UserRegistrationForm;
import com.example.bookstoreapplication.rdb.repositories.ClientRepository;
import com.example.bookstoreapplication.rdb.repositories.UserRepository;
import com.example.bookstoreapplication.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserRdbService implements UserService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRdbService(UserRepository userRepository,
                          ClientRepository clientRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean registration(UserRegistrationForm userRegistrationForm) {
        if (!Objects.equals(userRegistrationForm.getPassword(),userRegistrationForm.getPasswordConfirm())) {
            return false;
        }
        if (userRepository.findByLogin(userRegistrationForm.getLogin()).isPresent()) {
            return false;
        }
        User user = new User();
        user.setLogin(userRegistrationForm.getLogin());
        String passwordHash = passwordEncoder.encode(userRegistrationForm.getPassword());
        user.setPassword(passwordHash);
        user.setRole("USER");
        userRepository.save(user);
        Optional<User> optionalUser = userRepository.findByLogin(user.getLogin());
        if (optionalUser.isPresent()) {
            Client client = new Client();
            client.setUser(user);
            clientRepository.save(client);
        }
        return true;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
