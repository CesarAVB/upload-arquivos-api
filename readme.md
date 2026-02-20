# upload-arquivos-api

Microserviço responsável pelo upload de arquivos para buckets no MinIO, desenvolvido com Spring Boot e integrado via SDK AWS S3.

---

## 🚀 Tecnologias

- Java 17
- Spring Boot
- AWS SDK v2 (S3)
- MinIO
- Lombok

---

## 📁 Estrutura do Projeto

```
upload/
├── configuration/
│   └── MinioConfig.java
├── controller/
│   └── UploadController.java
├── dto/
│   └── UploadResponseDTO.java
│   └── response/
│       └── ErrorResponseDTO.java
├── exception/
│   ├── BucketException.java
│   ├── GlobalExceptionHandler.java
│   └── UploadException.java
├── service/
│   └── UploadService.java
```

---

## ⚙️ Configuração

Configure as variáveis de ambiente ou o `application.properties`:

```properties
minio.endpoint=${MINIO_ENDPOINT}
minio.access-key=${MINIO_ACCESSKEY}
minio.secret-key=${MINIO_SECRETKEY}
minio.region=${MINIO_REGION}

spring.servlet.multipart.max-file-size=100MB
spring.servlet.multipart.max-request-size=100MB
```

---

## 📡 Endpoints

### Upload de arquivo único

```http
POST /api/v1/upload/{bucket}/arquivo
Content-Type: multipart/form-data

Parâmetro: file (MultipartFile)
```

**Resposta:**
```json
{
  "nomeArquivo": "uuid-nome-do-arquivo.pdf",
  "url": "http://localhost:9000/meu-bucket/uuid-nome-do-arquivo.pdf"
}
```

---

### Upload de múltiplos arquivos

```http
POST /api/v1/upload/{bucket}/arquivos
Content-Type: multipart/form-data

Parâmetro: files (List<MultipartFile>)
```

**Resposta:**
```json
[
  {
    "nomeArquivo": "uuid-arquivo1.pdf",
    "url": "http://localhost:9000/meu-bucket/uuid-arquivo1.pdf"
  },
  {
    "nomeArquivo": "uuid-arquivo2.png",
    "url": "http://localhost:9000/meu-bucket/uuid-arquivo2.png"
  }
]
```

---

## ⚠️ Tratamento de Erros

Em caso de erro, a API retorna o seguinte padrão:

```json
{
  "timestamp": "2026-02-20T15:12:00Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Descrição do erro"
}
```

| Exceção | Status HTTP |
|---|---|
| `UploadException` | 500 Internal Server Error |
| `BucketException` | 500 Internal Server Error |
| `MaxUploadSizeExceededException` | 413 Payload Too Large |

---

## 📝 Observações

- O bucket é definido dinamicamente via URL. Caso não exista, é criado automaticamente.
- Aceita qualquer tipo de arquivo.
- O nome do arquivo salvo no MinIO é gerado com UUID para evitar conflitos.