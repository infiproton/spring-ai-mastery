package com.infiproton.springaidemo.controller;

import com.infiproton.springaidemo.service.AudioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/ai/audio")
@AllArgsConstructor
class AudioController {

    private final AudioService audioService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadAudio(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = audioService.store(file);
        return ResponseEntity.ok(response);
    }
}
