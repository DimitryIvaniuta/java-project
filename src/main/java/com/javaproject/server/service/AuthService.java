package com.javaproject.server.service;

import com.javaproject.server.data.entity.User;
import com.javaproject.server.data.repository.UserRepository;
import com.javaproject.server.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    /**
     * User Repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Password Encoder.
     */
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    /**
     * Authenticate User handler.
     *
     * @param email    an user email to login
     * @param password an user password
     * @return a Logged User
     * @throws Exception authenticate Exception
     */
    public User authenticateUser(final String email, final String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        return user;
    }

}
