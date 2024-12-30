package com.dirtymaster.fgpt.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApiKeyService {
    public static final String fileName = "key.txt";

    public static String getKeyFromFile() throws IOException, URISyntaxException {
        try(InputStream inputStream = ApiKeyService.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        }

        // Затем пробуем из директории с jar
        try {
            String jarPath = new File(ApiKeyService.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()).getPath();
            String directory = new File(jarPath).getParent();
            Path keyPath = Paths.get(directory, fileName);
            if (Files.exists(keyPath)) {
                return Files.readString(keyPath);
            }
        } catch (Exception _) {
        }

        // Наконец, пробуем из текущей директории
        Path currentPath = Paths.get(fileName);
        if (Files.exists(currentPath)) {
            return Files.readString(currentPath);
        }

        throw new FileNotFoundException(String.format("File %s not found", fileName));
    }
}
