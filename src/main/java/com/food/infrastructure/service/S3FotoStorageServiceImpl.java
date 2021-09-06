package com.food.infrastructure.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.food.config.StorageProperties;
import com.food.domain.exception.StorageException;
import com.food.service.FotoStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class S3FotoStorageServiceImpl implements FotoStorageService {

    private static final Logger LOGGER = LogManager.getLogger(S3FotoStorageServiceImpl.class);
    @Autowired
    private AmazonS3 amazonS3;
    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            var caminhoArquivo = getCaminhoArquivo(novaFoto.nomeArquivo());
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.contentType());
            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.fotoInputStream(),
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            LOGGER.error(e);
            throw  new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
        }
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s",
                storageProperties.getS3().getDiretorioFotos(),
                nomeArquivo);
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            var caminhoArquivo = getCaminhoArquivo(nomeArquivo);

            var deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(), caminhoArquivo);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            var caminhoArquivo = getCaminhoArquivo(nomeArquivo);
            var url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);
            return new FotoRecuperada(null, url.toString());
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo na Amazon S3.", e);
        }
    }
}
