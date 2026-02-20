package br.com.cesaravb.upload.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.cesaravb.upload.dto.response.UploadResponseDTO;
import br.com.cesaravb.upload.exception.BucketException;
import br.com.cesaravb.upload.exception.UploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadService {

    private final S3Client s3Client;

    @Value("${minio.endpoint}")
    private String endpoint;

    // ====================================
    // # 
    // ====================================
    public UploadResponseDTO upload(String bucket, MultipartFile file) {
        garantirBucketExiste(bucket);

        try {
            String nomeArquivo = UUID.randomUUID() + "-" + file.getOriginalFilename();

            var request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(nomeArquivo)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String url = endpoint + "/" + bucket + "/" + nomeArquivo;
            log.info("Arquivo enviado: {}", url);

            return new UploadResponseDTO(nomeArquivo, url);

        } catch (Exception e) {
            throw new UploadException("Erro ao enviar arquivo " + file.getOriginalFilename() + ": " + e.getMessage());
        }
    }

    
    // ====================================
    // # 
    // ====================================
    public List<UploadResponseDTO> uploadLote(String bucket, List<MultipartFile> files) {
        return files.stream().map(file -> upload(bucket, file)).toList();
    }

    
    // ====================================
    // # 
    // ====================================
    private void garantirBucketExiste(String bucket) {
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucket).build());
            log.info("Bucket já existe: {}", bucket);
            
        } catch (NoSuchBucketException e) {
        	
            try {
                log.info("Bucket não encontrado, criando: {}", bucket);
                s3Client.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
                
            } catch (Exception ex) {
                throw new BucketException("Erro ao criar bucket " + bucket + ": " + ex.getMessage());
            }
            
        } catch (Exception e) {
            throw new BucketException("Erro ao verificar bucket " + bucket + ": " + e.getMessage());
        }
    }
}