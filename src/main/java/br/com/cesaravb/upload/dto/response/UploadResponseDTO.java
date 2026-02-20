package br.com.cesaravb.upload.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResponseDTO {
    private String nomeArquivo;
    private String url;
}