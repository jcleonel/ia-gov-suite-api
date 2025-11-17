package br.com.iagovsuite.api.controller.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotBlank(message = "O primeiro nome não pode estar em branco")
        String firstName,

        @NotBlank(message = "O sobrenome não pode estar em branco")
        String lastName,

        @NotBlank(message = "O e-mail não pode estar em branco")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "A senha não pode estar em branco")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        String password
) {
}
