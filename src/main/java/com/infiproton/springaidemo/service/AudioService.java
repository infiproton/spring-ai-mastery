package com.infiproton.springaidemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AudioService {

    private static final Path AUDIO_DIR = Path.of(System.getProperty("java.io.tmpdir"), "spring-ai-audio");

    public Map<String, Object> store(MultipartFile file) {
        try {
            Files.createDirectories(AUDIO_DIR);

            String fileId = UUID.randomUUID().toString();
            String storedFileName = fileId + "_" + file.getOriginalFilename();
            Path targetPath = AUDIO_DIR.resolve(storedFileName);

            Files.copy(file.getInputStream(), targetPath);

            Map<String, Object> response = new HashMap<>();
            response.put("fileId", fileId);
            response.put("originalFilename", file.getOriginalFilename());
            response.put("contentType", file.getContentType());
            response.put("size", file.getSize());
            return response;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store audio file", e);
        }
    }
}
