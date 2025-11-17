package br.com.iagovsuite.api.service;

import br.com.iagovsuite.api.controller.project.dto.DocumentFileDto;
import br.com.iagovsuite.api.domain.project.AnalysisProject;
import br.com.iagovsuite.api.domain.project.DocumentFile;
import br.com.iagovsuite.api.domain.project.DocumentFileRepository;
import br.com.iagovsuite.api.domain.user.User;
import br.com.iagovsuite.api.exception.FileStorageException;
import br.com.iagovsuite.api.service.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private AnalysisProjectService analysisProjectService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private DocumentFileRepository documentFileRepository;

    @Transactional
    public DocumentFileDto uploadDocument(UUID projectId, MultipartFile file, User user) {
        AnalysisProject project = analysisProjectService.findProjectByIdAndVerifyAccess(projectId, user);

        DocumentFile existingDocument = project.getDocumentFile();
        if (existingDocument != null) {
            try {
                fileStorageService.delete(existingDocument.getStorageKey());
            } catch (IOException e) {
                System.err.println("Falha ao deletar arquivo antigo: " + existingDocument.getStorageKey());
            }
            project.setDocumentFile(null);
            documentFileRepository.delete(existingDocument);
        }

        String storageKey = fileStorageService.store(file);

        DocumentFile newDocument = new DocumentFile();
        newDocument.setOriginalFilename(file.getOriginalFilename());
        newDocument.setMimeType(file.getContentType());
        newDocument.setFileSize(file.getSize());
        newDocument.setStorageKey(storageKey);

        project.setDocumentFile(newDocument);

        DocumentFile savedDocument = documentFileRepository.save(newDocument);

        return DocumentFileDto.fromEntity(savedDocument);
    }

    @Transactional(readOnly = true)
    public Resource loadDocument(UUID projectId, User user) {
        AnalysisProject project = analysisProjectService.findProjectByIdAndVerifyAccess(projectId, user);

        DocumentFile document = project.getDocumentFile();
        if (document == null) {
            throw new FileStorageException("Nenhum documento encontrado para o projeto " + projectId);
        }

        return fileStorageService.loadAsResource(document.getStorageKey());
    }

    public String getDocumentMimeType(UUID projectId, User user) {
        AnalysisProject project = analysisProjectService.findProjectByIdAndVerifyAccess(projectId, user);
        if (project.getDocumentFile() == null) {
            throw new FileStorageException("Nenhum documento encontrado para o projeto " + projectId);
        }
        return project.getDocumentFile().getMimeType();
    }
}
