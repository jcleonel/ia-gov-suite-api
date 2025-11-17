package br.com.iagovsuite.api.controller.project.dto;

import br.com.iagovsuite.api.domain.project.DocumentFile;

import java.util.UUID;

public record DocumentFileDto(
        UUID id,
        String originalFilename,
        String mimeType,
        Long fileSize
) {
    public static DocumentFileDto fromEntity(DocumentFile documentFile) {
        if (documentFile == null) {
            return null;
        }
        return new DocumentFileDto(
                documentFile.getId(),
                documentFile.getOriginalFilename(),
                documentFile.getMimeType(),
                documentFile.getFileSize()
        );
    }
}
