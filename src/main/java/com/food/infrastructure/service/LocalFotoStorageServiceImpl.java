package com.food.infrastructure.service;

import com.food.config.StorageProperties;
import com.food.domain.exception.StorageException;
import com.food.service.FotoStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageServiceImpl implements FotoStorageService {

    private static final Logger LOGGER = LogManager.getLogger(LocalFotoStorageServiceImpl.class);

    private final StorageProperties storageProperties;

    @Autowired
    public LocalFotoStorageServiceImpl(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try (OutputStream destino = Files.newOutputStream(getArquivoPath(novaFoto.nomeArquivo()))){
            FileCopyUtils.copy(novaFoto.fotoInputStream(), destino);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new StorageException("Não foi possível armazenar o arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        Path fotoPath = getArquivoPath(nomeArquivo);
        try {
            Files.deleteIfExists(fotoPath);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new StorageException("Não foi possível excluir o arquivo.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            return new FotoRecuperada(Files.newInputStream(getArquivoPath(nomeArquivo)), null);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new StorageException("Não foi possível recuperar o arquivo.", e);
        }
    }

    private Path getArquivoPath(String fileName) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(fileName));
    }
}
