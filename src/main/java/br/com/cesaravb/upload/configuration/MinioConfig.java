package br.com.cesaravb.upload.configuration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "minio", name = "endpoint")
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .serviceConfiguration(S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build())
                .build();
    }
    
//    @Bean
//    public S3Presigner s3Presigner() {
//        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
//        S3Configuration s3Config = S3Configuration.builder().pathStyleAccessEnabled(true).build();
//
//        return S3Presigner.builder()
//                .endpointOverride(URI.create(endpoint))
//                .region(Region.of(region))
//                .credentialsProvider(StaticCredentialsProvider.create(credentials))
//                .serviceConfiguration(s3Config)
//                .build();
//    }
}