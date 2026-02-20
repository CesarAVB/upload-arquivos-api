package br.com.cesaravb.upload.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cesaravb.upload.dto.response.UploadResponseDTO;
import br.com.cesaravb.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1/upload", produces = {"application/json"})
@Slf4j
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    // ====================================
    // Salvar um único arquivo
    // ====================================
    @PostMapping("/{bucket}/arquivo")
    public ResponseEntity<UploadResponseDTO> salvarArquivo(@PathVariable String bucket, @RequestParam("file") MultipartFile file) {
        log.info("Arquivo recebido: {} para o bucket: {}", file.getOriginalFilename(), bucket);
        return ResponseEntity.ok(uploadService.upload(bucket, file));
    }

    // ====================================
    // Salvar múltiplos arquivos em lote
    // ====================================
    @PostMapping("/{bucket}/arquivos")
    public ResponseEntity<List<UploadResponseDTO>> salvarArquivos(@PathVariable String bucket, @RequestParam("files") List<MultipartFile> files) {
        log.info("{} arquivo(s) recebido(s) para o bucket: {}", files.size(), bucket);
        return ResponseEntity.ok(uploadService.uploadLote(bucket, files));
    }
}