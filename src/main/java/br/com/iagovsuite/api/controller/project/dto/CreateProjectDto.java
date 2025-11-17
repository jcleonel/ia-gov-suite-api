package br.com.iagovsuite.api.controller.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectDto(
        @NotBlank(message = "O título não pode estar em branco")
        @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
        String title,

        @Size(max = 255, message = "O nome do cliente deve ter no máximo 255 caracteres")
        String clientName
) {
}
