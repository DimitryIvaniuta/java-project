package com.javaproject.server.controller;


import com.javaproject.server.data.entity.User;
import com.javaproject.server.dto.UserRequestDTO;
import com.javaproject.server.dto.UserResponseDTO;
import com.javaproject.server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class UserController {

    /**
     * Service to manage users.
     */
    private final UserService userService;

    /**
     * UserController constructor.
     *
     * @param userService a user service
     */
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Create user.
     *
     * @param request user data to create
     * @return response object
     */
    @PostMapping("/add-user")
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody final UserRequestDTO request) {
        User user = userService.createUser(request);
        UserResponseDTO response = UserResponseDTO.toUserDTO(user, "User created successfully");
        return ResponseEntity.ok(response);
    }

}
