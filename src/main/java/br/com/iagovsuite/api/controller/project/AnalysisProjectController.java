package br.com.iagovsuite.api.controller.project;

import br.com.iagovsuite.api.controller.project.dto.AnalysisProjectDetailsDto;
import br.com.iagovsuite.api.controller.project.dto.AnalysisProjectDto;
import br.com.iagovsuite.api.controller.project.dto.CreateProjectDto;
import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.service.AnalysisProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class AnalysisProjectController {

    @Autowired
    private AnalysisProjectService analysisProjectService;

    @PostMapping
    public ResponseEntity<AnalysisProjectDto> createProject(
            @RequestBody @Valid CreateProjectDto dto,
            @AuthenticationPrincipal User authenticatedUser) {

        AnalysisProjectDto newProject = analysisProjectService.createProject(dto, authenticatedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProject);
    }

    @GetMapping
    public ResponseEntity<List<AnalysisProjectDto>> getMyProjects(
            @AuthenticationPrincipal User authenticatedUser) {

        List<AnalysisProjectDto> projects = analysisProjectService.listProjectsByUser(authenticatedUser);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<AnalysisProjectDetailsDto> getProjectDetails(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User authenticatedUser) {

        AnalysisProjectDetailsDto projectDetails = analysisProjectService.getProjectDetails(projectId, authenticatedUser);
        return ResponseEntity.ok(projectDetails);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<AnalysisProjectDto> updateProject(
            @PathVariable UUID projectId,
            @RequestBody @Valid CreateProjectDto dto,
            @AuthenticationPrincipal User authenticatedUser) {

        AnalysisProjectDto updatedProject = analysisProjectService.updateProject(projectId, dto, authenticatedUser);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User authenticatedUser) {

        analysisProjectService.deleteProject(projectId, authenticatedUser);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}
