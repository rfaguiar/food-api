package com.food.infrastructure.service;

import com.food.domain.exception.StorageException;
import com.food.service.FotoStorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageServiceImpl implements FotoStorageService {

    private static final Logger LOGGER = LogManager.getLogger(LocalFotoStorageServiceImpl.class);
    @Value("${food.storage.local.diretorio-fotos}")
    private Path pathDiretorioFotoLocal;

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

    private Path getArquivoPath(String fileName) {
        return pathDiretorioFotoLocal.resolve(Path.of(fileName));
    }
}
