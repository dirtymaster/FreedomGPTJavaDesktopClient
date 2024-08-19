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
        File keyFile = new File(jarPath, "key.txt");

        if (keyFile.exists()) {
            Path path = Paths.get(keyFile.getAbsolutePath());
            String content;
            try {
                content = new String(Files.readAllBytes(path));
            } catch (IOException ex) {
                return;
            }
            this.apiKey = content;
        } else {
            System.out.println("Файл key.txt не найден");
        }
    }
}
