package br.com.iagovsuite.api.controller.project.dto;

import br.com.iagovsuite.api.domain.project.RiskLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpsertAnnotationDto(
        @NotNull(message = "O nível de risco não pode ser nulo")
        RiskLevel riskLevel,

        String selectedText,

        @NotBlank(message = "A recomendação não pode estar em branco")
        String recommendation
) {
}
