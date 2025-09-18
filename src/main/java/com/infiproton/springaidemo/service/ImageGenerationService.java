package com.infiproton.springaidemo.service;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ImageGenerationService {
    private final ImageModel imageModel;

    public ImageGenerationService(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public byte[] generate(String message) {
        ImageResponse imageResponse = imageModel.call(new ImagePrompt(message,
                OpenAiImageOptions.builder()
                        .responseFormat("b64_json")
                        .width(1792)
                        .height(1024)
                        .quality("hd")
                        .build()));
        String b64 = imageResponse.getResult().getOutput().getB64Json();
        return Base64.getDecoder().decode(b64);
    }
}
