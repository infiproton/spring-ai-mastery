package com.infiproton.springaidemo.controller;

import com.infiproton.springaidemo.model.ChatRequest;
import com.infiproton.springaidemo.service.ImageCaptionService;
import com.infiproton.springaidemo.service.ImageGenerationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/image")
class ImageRestController {

    private final ImageCaptionService imageCaptionService;
    private final ImageGenerationService imageGenerationService;

    ImageRestController(ImageCaptionService imageCaptionService, ImageGenerationService imageGenerationService) {
        this.imageCaptionService = imageCaptionService;
        this.imageGenerationService = imageGenerationService;
    }

    @PostMapping("/caption")
    public String caption(@RequestBody ChatRequest chatRequest) {
        return imageCaptionService.captionImage(chatRequest.getImageName(), chatRequest.getMessage());
    }

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateImage(@RequestParam String message) {
        byte[] imageBytes = imageGenerationService.generate(message);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageBytes);
    }
}
