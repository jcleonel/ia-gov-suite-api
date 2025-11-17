package br.com.iagovsuite.api.service;

import br.com.iagovsuite.api.controller.project.dto.AnnotationDto;
import br.com.iagovsuite.api.controller.project.dto.UpsertAnnotationDto;
import br.com.iagovsuite.api.domain.project.AnalysisProject;
import br.com.iagovsuite.api.domain.project.Annotation;
import br.com.iagovsuite.api.domain.project.AnnotationRepository;
import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.exception.AnnotationNotFoundException;
import br.com.iagovsuite.api.exception.ProjectAccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AnnotationService {

    @Autowired
    private AnnotationRepository annotationRepository;

    @Autowired
    private AnalysisProjectService analysisProjectService;

    @Transactional
    public AnnotationDto createAnnotation(UUID projectId, UpsertAnnotationDto dto, User user) {
        AnalysisProject project = analysisProjectService.findProjectByIdAndVerifyAccess(projectId, user);

        Annotation newAnnotation = new Annotation();
        newAnnotation.setRiskLevel(dto.riskLevel());
        newAnnotation.setSelectedText(dto.selectedText());
        newAnnotation.setRecommendation(dto.recommendation());

        project.addAnnotation(newAnnotation);

        annotationRepository.save(newAnnotation);

        return AnnotationDto.fromEntity(newAnnotation);
    }

    @Transactional
    public AnnotationDto updateAnnotation(UUID projectId, UUID annotationId, UpsertAnnotationDto dto, User user) {
        analysisProjectService.findProjectByIdAndVerifyAccess(projectId, user);

        Annotation annotation = findAndVerifyAnnotation(projectId, annotationId);

        annotation.setRiskLevel(dto.riskLevel());
        annotation.setSelectedText(dto.selectedText());
        annotation.setRecommendation(dto.recommendation());

        Annotation updatedAnnotation = annotationRepository.save(annotation);
        return AnnotationDto.fromEntity(updatedAnnotation);
    }

    @Transactional
    public void deleteAnnotation(UUID projectId, UUID annotationId, User user) {
        analysisProjectService.findProjectByIdAndVerifyAccess(projectId, user);
        Annotation annotation = findAndVerifyAnnotation(projectId, annotationId);
        annotationRepository.delete(annotation);
    }

    private Annotation findAndVerifyAnnotation(UUID projectId, UUID annotationId) {
        Annotation annotation = annotationRepository.findById(annotationId)
                .orElseThrow(() -> new AnnotationNotFoundException("Anotação com ID " + annotationId + " não encontrada."));

        if (!annotation.getAnalysisProject().getId().equals(projectId)) {
            throw new ProjectAccessDeniedException("Esta anotação não pertence ao projeto " + projectId);
        }

        return annotation;
    }
}
