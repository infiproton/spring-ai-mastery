package com.infiproton.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String message) {
        return chatClient.prompt()
                .system("You are an experience Spring Boot trainer, explain details in step-by-step in bullet points")
                .user(message)
                .call().content();
    }

}
