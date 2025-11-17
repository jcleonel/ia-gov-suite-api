package br.com.iagovsuite.api.controller.project;

import br.com.iagovsuite.api.controller.project.dto.DocumentFileDto;
import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public ResponseEntity<DocumentFileDto> uploadDocument(
            @PathVariable UUID projectId,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user) {

        DocumentFileDto dto = documentService.uploadDocument(projectId, file, user);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User user) {

        Resource resource = documentService.loadDocument(projectId, user);
        String mimeType = documentService.getDocumentMimeType(projectId, user);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
