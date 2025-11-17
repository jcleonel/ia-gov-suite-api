package br.com.iagovsuite.api.controller.project.dto;

import br.com.iagovsuite.api.controller.auth.dto.UserDto;
import br.com.iagovsuite.api.domain.project.AnalysisProject;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record AnalysisProjectDetailsDto(
        UUID id,
        String title,
        String clientName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        UserDto owner,
        DocumentFileDto document,
        List<AnnotationDto> annotations
) {
    public static AnalysisProjectDetailsDto fromEntity(AnalysisProject project) {
        return new AnalysisProjectDetailsDto(
                project.getId(),
                project.getTitle(),
                project.getClientName(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                UserDto.fromEntity(project.getUser()),
                DocumentFileDto.fromEntity(project.getDocumentFile()),
                project.getAnnotations().stream()
                        .map(AnnotationDto::fromEntity)
                        .collect(Collectors.toList())
        );
    }
}
