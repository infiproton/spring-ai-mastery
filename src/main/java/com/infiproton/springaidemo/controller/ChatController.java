package com.infiproton.springaidemo.controller;

import com.infiproton.springaidemo.model.ChatRequest;
import com.infiproton.springaidemo.model.TravelPlan;
import com.infiproton.springaidemo.service.ChatService;
import com.infiproton.springaidemo.service.TravelGuideService;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
class ChatController {
    private final ChatService chatService;
    private final TravelGuideService travelGuideService;

    ChatController(ChatService chatService, TravelGuideService travelGuideService) {
        this.chatService = chatService;
        this.travelGuideService = travelGuideService;
    }

    @GetMapping("/travel-guide")
    public TravelPlan prepareTravelPlan(@RequestParam String city, @RequestParam Integer days) {
        return travelGuideService.prepareTravelPlan(city, days);
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest chatRequest) {
        return chatService.chat(chatRequest.getMessage());
    }
}
