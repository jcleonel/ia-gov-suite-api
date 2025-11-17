package br.com.iagovsuite.api.controller.project;

import br.com.iagovsuite.api.controller.project.dto.AnnotationDto;
import br.com.iagovsuite.api.controller.project.dto.UpsertAnnotationDto;
import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.service.AnnotationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/annotations")
public class AnnotationController {

    @Autowired
    private AnnotationService annotationService;

    @PostMapping
    public ResponseEntity<AnnotationDto> createAnnotation(
            @PathVariable UUID projectId,
            @RequestBody @Valid UpsertAnnotationDto dto,
            @AuthenticationPrincipal User user) {

        AnnotationDto newAnnotation = annotationService.createAnnotation(projectId, dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnotation);
    }

    @PutMapping("/{annotationId}")
    public ResponseEntity<AnnotationDto> updateAnnotation(
            @PathVariable UUID projectId,
            @PathVariable UUID annotationId,
            @RequestBody @Valid UpsertAnnotationDto dto,
            @AuthenticationPrincipal User user) {

        AnnotationDto updatedAnnotation = annotationService.updateAnnotation(projectId, annotationId, dto, user);
        return ResponseEntity.ok(updatedAnnotation);
    }

    @DeleteMapping("/{annotationId}")
    public ResponseEntity<Void> deleteAnnotation(
            @PathVariable UUID projectId,
            @PathVariable UUID annotationId,
            @AuthenticationPrincipal User user) {

        annotationService.deleteAnnotation(projectId, annotationId, user);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

}
