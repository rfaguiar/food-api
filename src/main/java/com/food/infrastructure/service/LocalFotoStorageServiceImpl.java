package com.food.infrastructure.service;

import com.food.api.model.request.FotoProdutoRequest;
import com.food.domain.exception.StorageException;
import com.food.service.FotoStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class LocalFotoStorageServiceImpl implements FotoStorageService {

    private static final Logger LOGGER = LogManager.getLogger(LocalFotoStorageServiceImpl.class);
    @Value("${food.storage.local.diretorio-fotos}")
    private Path pathDiretorioFotoLocal;

    @Override
    public void armazenar(FotoProdutoRequest fotoProdutoRequest) {
        try (InputStream fotoInputStream = fotoProdutoRequest.getArquivo().getInputStream();
             OutputStream destino = Files.newOutputStream(getArquivoPath(fotoProdutoRequest))){
            FileCopyUtils.copy(fotoInputStream, destino);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new StorageException("Não foi possível armazenar o arquivo.", e);
        }
    }

    private Path getArquivoPath(FotoProdutoRequest fotoProdutoRequest) {
        String originalFilename = fotoProdutoRequest.getArquivo().getOriginalFilename();
        String nomeArquivo = UUID.randomUUID().toString() + "_" + originalFilename;
        return pathDiretorioFotoLocal.resolve(
                Path.of(nomeArquivo));
    }
}
