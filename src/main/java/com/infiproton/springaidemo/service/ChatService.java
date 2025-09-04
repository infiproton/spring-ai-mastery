package com.infiproton.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
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
                new SystemMessage("You are a friendly travel guide. Always suggest 3 attractions and 1 food item. "),
                new UserMessage("Plan my day in Rome."),
                new AssistantMessage("Morning: Visit the Colosseum\nAfternoon: Explore the Vatican Museums\nEvening: See the Trevi Fountain.  \nFood: Gelato.")
        ));
        return chatClient.prompt(prompt)
                .user(message)
                .call().content();
    }

}
