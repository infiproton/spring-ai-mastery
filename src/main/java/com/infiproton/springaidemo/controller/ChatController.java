package com.infiproton.springaidemo.controller;

import com.infiproton.springaidemo.model.ChatRequest;
import com.infiproton.springaidemo.model.TravelPlan;
import com.infiproton.springaidemo.rag.VectorStoreService;
import com.infiproton.springaidemo.service.ChatService;
import com.infiproton.springaidemo.service.TravelGuideService;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ai")
class ChatController {

    private final ChatService chatService;
    private final TravelGuideService travelGuideService;
    @Autowired
    private ChatMemory chatMemory;
    private final VectorStoreService vectorStoreService;

    ChatController(ChatService chatService, TravelGuideService travelGuideService, VectorStoreService vectorStoreService) {
        this.chatService = chatService;
        this.travelGuideService = travelGuideService;
        this.vectorStoreService = vectorStoreService;
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
