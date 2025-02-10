package com.javaproject.server.dto;

import com.javaproject.server.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    /**
     * User created ID.
     */
    private Long id;

    /**
     * User created message.
     */
    private String message;

    /**
     * Convert User entity to UserResponseDTO.
     *
     * @param user    anentity
     * @param message an info message
     * @return an UserResponseDTO
     */
    public static UserResponseDTO toUserDTO(final User user, final String message) {
        return new UserResponseDTO(user.getId(), message);
    }
}
