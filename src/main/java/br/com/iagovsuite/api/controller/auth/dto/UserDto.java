package br.com.iagovsuite.api.controller.auth.dto;

import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.domain.user.UserRole;

import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        UserRole role
) {

    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

}
