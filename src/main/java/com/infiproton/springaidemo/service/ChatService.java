package com.infiproton.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    ChatService(ChatClient chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.chatMemory = chatMemory;
    }

    public String chat(String conversationId, String message) {
        // generate id if not passed
        String convId = (conversationId == null || conversationId.isBlank())
                ? UUID.randomUUID().toString()
                : conversationId;

        Prompt prompt = new Prompt(List.of(
                new SystemMessage("You are a friendly travel guide. Always suggest 3 attractions and 1 food item. ")
        ));
        return chatClient.prompt(prompt)
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .conversationId(convId)
                        .build())
                .user(message)
                .call().content();
    }

}
