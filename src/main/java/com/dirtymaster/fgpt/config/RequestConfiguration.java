package com.dirtymaster.fgpt.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Data
public class RequestConfiguration {
    private String url = "https://chat.freedomgpt.com/api/v1";
    private String apiKey;
    private String model = "gpt-4o";
    private int maxTokens = 2048;
    private BigDecimal temperature = BigDecimal.valueOf(0.7);
    private int topK;
    private BigDecimal topP;

    public RequestConfiguration() {
        String jarPath = System.getProperty("user.dir");

        String keyFileName = "key.txt";
        try {
            this.apiKey = readFileIfExists(jarPath, keyFileName);
        } catch (IOException e) {
            System.out.println("Файл не найден: " + keyFileName);
        }

        String urlFileName = "url.txt";
        try {
            String url = readFileIfExists(jarPath, urlFileName);
            if (url != null) {
                this.url = url;
            }
        } catch (IOException e) {
            System.out.println("Файл не найден: " + urlFileName);
        }
    }

    private String readFileIfExists(String jarPath, String fileName) throws IOException {
        File keyFile = new File(jarPath, fileName);
        if (keyFile.exists()) {
            Path path = Paths.get(keyFile.getAbsolutePath());
            return new String(Files.readAllBytes(path));
        } else {
            System.out.println("Файл key.txt не найден");
            return null;
        }
    }
}
