package com.infiproton.springaidemo.controller;

import com.infiproton.springaidemo.model.ChatRequest;
import com.infiproton.springaidemo.model.TravelPlan;
import com.infiproton.springaidemo.rag.VectorStoreService;
import com.infiproton.springaidemo.service.AudioService;
import com.infiproton.springaidemo.service.ChatService;
import com.infiproton.springaidemo.service.TravelGuideService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@AllArgsConstructor
class ChatController {

    private final ChatService chatService;
    private final TravelGuideService travelGuideService;
    @Autowired
    private ChatMemory chatMemory;
    private final VectorStoreService vectorStoreService;
    private final AudioService audioService;

    @PostMapping("/chat/audio")
    public Map<String, Object> chatWithAudio(@RequestParam("file") MultipartFile file) {
        // 1. upload audio
        Map<String, Object> uploadResult = audioService.store(file);
        String storedFileName = (String) uploadResult.get("storedFileName");

        // 2. convert audio to text
        String transcript = audioService.speechToText(storedFileName);

        // 3. send transcript to chat service
        String aiResponse = chatService.chat(null, transcript);

        // 4. return the response
        return Map.of("transcript", transcript, "aiResponse", aiResponse);
    }

    @GetMapping("/travel-guide")
    public TravelPlan prepareTravelPlan(@RequestParam String city, @RequestParam Integer days) {
        return travelGuideService.prepareTravelPlan(city, days);
    }

    @GetMapping("/memory")
    public List<Message> fetchMemory(@RequestParam String conversationId) {
        return chatMemory.get(conversationId);
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest chatRequest) {
        return chatService.chat(chatRequest.getConversationId(), chatRequest.getMessage());
    }


    @PostMapping("/load-pdfs")
    public String loadPdfs() throws IOException {
        vectorStoreService.initialize();
        return "Done.";
    }
}
