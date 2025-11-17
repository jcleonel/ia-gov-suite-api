package br.com.iagovsuite.api.service;

import br.com.iagovsuite.api.controller.project.dto.AnalysisProjectDetailsDto;
import br.com.iagovsuite.api.controller.project.dto.AnalysisProjectDto;
import br.com.iagovsuite.api.controller.project.dto.CreateProjectDto;
import br.com.iagovsuite.api.domain.project.AnalysisProject;
import br.com.iagovsuite.api.domain.project.AnalysisProjectRepository;
import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.exception.ProjectAccessDeniedException;
import br.com.iagovsuite.api.exception.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnalysisProjectService {

    @Autowired
    private AnalysisProjectRepository analysisProjectRepository;

    @Transactional
    public AnalysisProjectDto createProject(CreateProjectDto dto, User authenticatedUser) {
        AnalysisProject newProject = new AnalysisProject();
        newProject.setTitle(dto.title());
        newProject.setClientName(dto.clientName());
        newProject.setUser(authenticatedUser);

        AnalysisProject savedProject = analysisProjectRepository.save(newProject);
        return AnalysisProjectDto.fromEntity(savedProject);
    }

    @Transactional(readOnly = true)
    public List<AnalysisProjectDto> listProjectsByUser(User authenticatedUser) {
        return analysisProjectRepository.findByUserId(authenticatedUser.getId())
                .stream()
                .map(AnalysisProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnalysisProjectDetailsDto getProjectDetails(UUID projectId, User authenticatedUser) {
        AnalysisProject project = findProjectByIdAndVerifyAccess(projectId, authenticatedUser);
        return AnalysisProjectDetailsDto.fromEntity(project);
    }

    @Transactional
    public AnalysisProjectDto updateProject(UUID projectId, CreateProjectDto dto, User authenticatedUser) {
        AnalysisProject project = findProjectByIdAndVerifyAccess(projectId, authenticatedUser);

        project.setTitle(dto.title());
        project.setClientName(dto.clientName());

        AnalysisProject updatedProject = analysisProjectRepository.save(project);
        return AnalysisProjectDto.fromEntity(updatedProject);
    }

    @Transactional
    public void deleteProject(UUID projectId, User authenticatedUser) {
        AnalysisProject project = findProjectByIdAndVerifyAccess(projectId, authenticatedUser);
        analysisProjectRepository.delete(project);
    }

    public AnalysisProject findProjectByIdAndVerifyAccess(UUID projectId, User authenticatedUser) {
        AnalysisProject project = analysisProjectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Projeto com ID " + projectId + " não encontrado."));

        if (!project.getUser().getId().equals(authenticatedUser.getId())) {
            throw new ProjectAccessDeniedException("Você não tem permissão para acessar este projeto.");
        }

        return project;
    }
}