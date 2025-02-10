package com.javaproject.server.data.repository;

import com.javaproject.server.data.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Find user by email.
     *
     * @param email value
     * @return optional user.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by login.
     *
     * @param login value
     * @return optional user.
     */
    Optional<User> findByLogin(String login);

}
