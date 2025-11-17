package br.com.iagovsuite.api.service.storage;

import br.com.iagovsuite.api.exception.FileStorageException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    private final Path rootLocation;

    public LocalFileStorageService(@Value("${file.storage.location}") String storageLocation) {
        this.rootLocation = Paths.get(storageLocation);
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileStorageException("Não foi possível criar o diretório de armazenamento", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("Falha ao armazenar arquivo vazio.");
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        String storageKey = UUID.randomUUID() + "." + fileExtension; // Cria um nome único

        try (InputStream inputStream = file.getInputStream()) {
            Path destinationFile = this.rootLocation.resolve(storageKey).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new FileStorageException("Não é possível salvar o arquivo fora do diretório raiz.");
            }
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            return storageKey;
        } catch (IOException e) {
            throw new FileStorageException("Falha ao armazenar o arquivo.", e);
        }
    }

    @Override
    public Resource loadAsResource(String storageKey) {
        try {
            Path file = rootLocation.resolve(storageKey);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("Não foi possível ler o arquivo: " + storageKey);
            }
        } catch (MalformedURLException e) {
            throw new FileStorageException("Não foi possível ler o arquivo: " + storageKey, e);
        }
    }

    @Override
    public void delete(String storageKey) throws IOException {
        if (storageKey == null || storageKey.isEmpty()) {
            return;
        }
        try {
            Path file = rootLocation.resolve(storageKey);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new FileStorageException("Não foi possível deletar o arquivo: " + storageKey, e);
        }
    }
}
