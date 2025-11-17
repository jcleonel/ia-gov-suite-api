package br.com.iagovsuite.api.controller.project.dto;

import br.com.iagovsuite.api.domain.project.AnalysisProject;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AnalysisProjectDto(
        UUID id,
        String title,
        String clientName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        DocumentFileDto document
) {
    public static AnalysisProjectDto fromEntity(AnalysisProject project) {
        return new AnalysisProjectDto(
                project.getId(),
                project.getTitle(),
                project.getClientName(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                DocumentFileDto.fromEntity(project.getDocumentFile())
        );
    }
}
