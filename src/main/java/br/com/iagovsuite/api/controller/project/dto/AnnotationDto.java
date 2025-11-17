package br.com.iagovsuite.api.controller.project.dto;

import br.com.iagovsuite.api.domain.project.Annotation;
import br.com.iagovsuite.api.domain.project.RiskLevel;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AnnotationDto(
        UUID id,
        RiskLevel riskLevel,
        String selectedText,
        String recommendation,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static AnnotationDto fromEntity(Annotation annotation) {
        return new AnnotationDto(
                annotation.getId(),
                annotation.getRiskLevel(),
                annotation.getSelectedText(),
                annotation.getRecommendation(),
                annotation.getCreatedAt(),
                annotation.getUpdatedAt()
        );
    }
}