package com.food.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.food.infrastructure.service.LocalFotoStorageServiceImpl;
import com.food.infrastructure.service.S3FotoStorageServiceImpl;
import com.food.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Autowired
    public StorageConfig(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    public AmazonS3 createAmazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegiao())
                .build();
    }

    @Bean
    public FotoStorageService createFotoStorageService(AmazonS3 amazonS3, StorageProperties storageProperties) {
        if (StorageProperties.TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageServiceImpl(amazonS3, storageProperties);
        }
        return new LocalFotoStorageServiceImpl(storageProperties);
    }

}
