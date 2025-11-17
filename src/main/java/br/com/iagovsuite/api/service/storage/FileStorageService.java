package br.com.iagovsuite.api.service.storage;

import br.com.iagovsuite.api.exception.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String store(MultipartFile file) throws FileStorageException;

    Resource loadAsResource(String storageKey) throws FileStorageException;

    void delete(String storageKey) throws IOException;

}
