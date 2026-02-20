package br.com.cesaravb.upload.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import br.com.cesaravb.upload.dto.response.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BucketException.class)
    public ResponseEntity<ErrorResponseDTO> handleBucketException(BucketException e) {
        log.error("Erro no bucket: {}", e.getMessage());
        var error = ErrorResponseDTO.builder()
                .timestamp(Instant.now())
                .status(500)
                .error("Internal Server Error")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(UploadException.class)
    public ResponseEntity<ErrorResponseDTO> handleUploadException(UploadException e) {
        log.error("Erro no upload: {}", e.getMessage());
        var error = ErrorResponseDTO.builder()
                .timestamp(Instant.now())
                .status(500)
                .error("Internal Server Error")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handleMaxUploadSize(MaxUploadSizeExceededException e) {
        log.error("Arquivo excede o tamanho máximo permitido: {}", e.getMessage());
        var error = ErrorResponseDTO.builder()
                .timestamp(Instant.now())
                .status(413)
                .error("Payload Too Large")
                .message("Arquivo excede o tamanho máximo permitido")
                .build();
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception e) {
        log.error("Erro inesperado: {}", e.getMessage());
        var error = ErrorResponseDTO.builder()
                .timestamp(Instant.now())
                .status(500)
                .error("Internal Server Error")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}