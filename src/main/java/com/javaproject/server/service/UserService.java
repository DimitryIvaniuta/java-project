package com.javaproject.server.service;

import com.javaproject.server.data.entity.User;
import com.javaproject.server.data.repository.UserRepository;
import com.javaproject.server.dto.UserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;

@Service
public class UserService {

    /**
     * User entity repository.
     */
    private final UserRepository userRepository;

    /**
     * User password encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * UserService constructor.
     *
     * @param userRepository  an User repository
     * @param passwordEncoder an User password encoder.
     */
    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create user.
     *
     * @param request user object data
     * @return am User Entity
     */
    public User createUser(final UserRequestDTO request) {
        // Check if the email is already taken
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already in use.");
        }

        // Check if the login is already taken
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login is already taken.");
        }

        // Create and save user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setName(request.getName());
        user.setCreatedAt(ZonedDateTime.now());

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

}
