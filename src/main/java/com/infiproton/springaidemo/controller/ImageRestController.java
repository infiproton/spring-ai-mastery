package com.infiproton.springaidemo.controller;

import com.infiproton.springaidemo.model.ChatRequest;
import com.infiproton.springaidemo.service.ImageCaptionService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/ai/image")
class ImageRestController {

    private final ImageCaptionService imageCaptionService;

    ImageRestController(ImageCaptionService imageCaptionService) {
        this.imageCaptionService = imageCaptionService;
    }

    @PostMapping("/caption")
    public String caption(@RequestBody ChatRequest chatRequest) {
        return imageCaptionService.captionImage(chatRequest.getImageName(), chatRequest.getMessage());
    }
}
