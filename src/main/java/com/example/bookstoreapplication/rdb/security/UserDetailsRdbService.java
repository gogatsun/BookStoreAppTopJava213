package com.example.bookstoreapplication.rdb.security;

import com.example.bookstoreapplication.entity.User;
import com.example.bookstoreapplication.rdb.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsRdbService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsRdbService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsRdb(user.get());
    }
}
