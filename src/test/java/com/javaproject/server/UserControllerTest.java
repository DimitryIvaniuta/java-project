package com.javaproject.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaproject.server.data.entity.User;
import com.javaproject.server.data.repository.UserRepository;
import com.javaproject.server.dto.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository.deleteAll(); // Clean database before each test
    }


    @Test
    void testAddUser() throws Exception {
        // Prepare request DTO
        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("test@example.com");
        request.setLogin("testuser");
        request.setName("Test User");
        request.setPassword("securepassword");

        // Perform the API request
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/api/public/add-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(jsonPath("$.message").exists()); // Ensure response contains a message

        // Verify user was saved in database
        User savedUser = userRepository.findByEmail("test@example.com").orElse(null);
        assert savedUser != null;
        assert passwordEncoder.matches("securepassword", savedUser.getPassword()); // Password should be hashed
    }

    @Test
    void testDuplicateUserEmail() throws Exception {
        // Create an existing user
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setLogin("existinguser");
        existingUser.setName("Existing User");
        existingUser.setPassword(passwordEncoder.encode("password123"));
        existingUser.setCreatedAt(ZonedDateTime.now());
        userRepository.save(existingUser);

        // Attempt to register with the same email
        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("test@example.com");
        request.setLogin("newuser");
        request.setName("New User");
        request.setPassword("securepassword");

        // Perform API request
        mockMvc.perform(post("/api/public/add-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // Expect HTTP 400 due to duplicate email
    }

}
