package com.javaproject.server.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    /**
     * Primary Key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JP_UNIQUE_ID")
    @SequenceGenerator(name = "JP_UNIQUE_ID", sequenceName = "JP_UNIQUE_ID", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Created At.
     */
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    /**
     * Email.
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * Login.
     */
    @Column(name = "login", nullable = false)
    private String login;

    /**
     * Name.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Password.
     */
    @Column(name = "password", nullable = false)
    private String password;

}

