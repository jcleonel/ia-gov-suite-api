package br.com.iagovsuite.api.service.storage;

import br.com.iagovsuite.api.exception.FileStorageException;
//import io.awspring.cloud.s3.S3Exception;
//import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

//@Service
//@Profile("prod")
public class S3FileStorageService {

//    private final S3Template s3Template;
//    private final String bucketName;
//
//    public S3FileStorageService(S3Template s3Template,
//                                @Value("${aws.s3.bucket}") String bucketName) {
//        this.s3Template = s3Template;
//        this.bucketName = bucketName;
//    }
//
//    @Override
//    public String store(MultipartFile file) {
//        String originalFilename = file.getOriginalFilename();
//        String extension = originalFilename != null && originalFilename.contains(".")
//                ? originalFilename.substring(originalFilename.lastIndexOf("."))
//                : "";
//
//        String storageKey = UUID.randomUUID() + extension;
//
//        try {
//            s3Template.upload(bucketName, storageKey, file.getInputStream());
//            return storageKey;
//        } catch (IOException | S3Exception e) {
//            throw new FileStorageException("Erro ao enviar arquivo para o S3", e);
//        }
//    }
//
//    @Override
//    public Resource loadAsResource(String storageKey) {
//        try {
//            return s3Template.download(bucketName, storageKey);
//        } catch (S3Exception e) {
//            throw new FileStorageException("Arquivo não encontrado no S3: " + storageKey, e);
//        }
//    }
//
//    @Override
//    public void delete(String storageKey) {
//        try {
//            s3Template.deleteObject(bucketName, storageKey);
//        } catch (S3Exception e) {
//            throw new FileStorageException("Erro ao deletar arquivo do S3", e);
//        }
//    }
}
