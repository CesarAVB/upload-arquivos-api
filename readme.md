# 📦 Upload Arquivos API

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.11-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MinIO](https://img.shields.io/badge/MinIO-S3_Compatible-C72E49?style=for-the-badge&logo=minio&logoColor=white)
![AWS SDK](https://img.shields.io/badge/AWS_SDK-v2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

> Microserviço RESTful para upload de arquivos individuais e em lote para o MinIO via protocolo S3, com criação automática de buckets e tratamento global de exceções.

---

## 🚀 Funcionalidades

- Upload de **arquivo único** para um bucket específico
- Upload de **múltiplos arquivos** em lote
- **Criação automática** de bucket caso não exista
- Nome do arquivo gerado com **UUID** para evitar colisões
- **Tratamento global de exceções** com respostas padronizadas
- Configuração de **CORS** via propriedades
- Suporte a múltiplos **perfis** (`local`, `prod`)

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.5.11 | Framework base |
| AWS SDK v2 (S3) | 2.20.26 | Comunicação com MinIO |
| Lombok | — | Redução de boilerplate |
| Maven | — | Build e dependências |

---

## ⚙️ Configuração

### Variáveis de Ambiente (perfil `prod`)

| Variável | Descrição |
|---|---|
| `MINIO_ENDPOINT` | URL do servidor MinIO (ex: `http://localhost:9000`) |
| `MINIO_ACCESSKEY` | Access Key do MinIO |
| `MINIO_SECRETKEY` | Secret Key do MinIO |
| `MINIO_REGION` | Região (ex: `us-east-1`) |
| `CORS_ALLOWED_ORIGINS` | Origins permitidas (separadas por vírgula) |
| `SPRING_PROFILES_ACTIVE` | Perfil ativo (`local` ou `prod`) |

### Exemplo — `application-local.properties`

```properties
minio.endpoint=http://localhost:9000
minio.access-key=minioadmin
minio.secret-key=minioadmin
minio.region=us-east-1
app.cors.allowed-origins=http://localhost:4200
```

---

## 📡 Endpoints

### Upload de arquivo único

```http
POST /api/v1/upload/{bucket}/arquivo
Content-Type: multipart/form-data

Param: file (MultipartFile)
```

### Upload em lote

```http
POST /api/v1/upload/{bucket}/arquivos
Content-Type: multipart/form-data

Param: files (List<MultipartFile>)
```

### Resposta de sucesso

```json
{
  "nomeArquivo": "550e8400-e29b-41d4-a716-446655440000-foto.png",
  "url": "http://localhost:9000/meu-bucket/550e8400-e29b-41d4-a716-446655440000-foto.png"
}
```

### Resposta de erro

```json
{
  "timestamp": "2025-01-01T12:00:00Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Erro ao enviar arquivo foto.png: ..."
}
```

---

## ▶️ Como executar

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/upload-arquivos-api.git

# Acesse a pasta
cd upload-arquivos-api

# Execute com Maven (perfil local)
./mvnw spring-boot:run
```

> **Pré-requisito:** MinIO em execução. Utilize Docker:
> ```bash
> docker run -p 9000:9000 -p 9001:9001 \
>   -e MINIO_ROOT_USER=minioadmin \
>   -e MINIO_ROOT_PASSWORD=minioadmin \
>   quay.io/minio/minio server /data --console-address ":9001"
> ```

---

## 🔮 Melhorias Futuras

- [ ] **Validação de tipo de arquivo** — restringir extensões permitidas por bucket (ex: apenas imagens, apenas PDFs)
- [ ] **Limite de tamanho configurável por bucket** — além do limite global do Spring
- [ ] **Endpoint de deleção de arquivo** — `DELETE /api/v1/upload/{bucket}/arquivo/{nomeArquivo}`
- [ ] **Endpoint de listagem de arquivos** — listar objetos de um bucket com paginação
- [ ] **Geração de URL pré-assinada** — URL temporária de acesso direto ao arquivo (S3Presigner já mapeado no código)
- [ ] **Autenticação e autorização** — integração com Spring Security + JWT
- [ ] **Testes unitários e de integração** — cobertura do `UploadService` com Mockito e Testcontainers (MinIO)
- [ ] **Docker Compose completo** — orquestrar API + MinIO em um único arquivo
- [ ] **Suporte a múltiplas regiões/instâncias** — configuração dinâmica de múltiplos clientes S3
- [ ] **Endpoint de download** — retornar o arquivo como stream via `ResponseEntity<Resource>`
- [ ] **Documentação com Swagger/OpenAPI** — interface interativa para testar os endpoints

---

## 👨‍💻 Autor

**Cesar Augusto Vieira Bezerra**  
[portfolio.cesaraugusto.dev.br](https://portfolio.cesaraugusto.dev.br/)
