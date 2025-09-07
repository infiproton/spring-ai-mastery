package com.infiproton.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatClient chatClient;

    ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String message) {
        Prompt prompt = new Prompt(List.of(
                new SystemMessage("You are a friendly travel guide. Always suggest 3 attractions and 1 food item. ")
        ));
        return chatClient.prompt(prompt)
                .user(message)
                .call().content();
    }

}
