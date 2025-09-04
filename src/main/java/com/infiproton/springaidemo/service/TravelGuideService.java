package com.infiproton.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TravelGuideService {

    private final ChatClient chatClient;
    PromptTemplate template = new PromptTemplate(
            "Create a {days}-day travel plan for {city}."
    );

    TravelGuideService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String prepareTravelPlan(String city, Integer days) {
        Map<String, Object> params = Map.of(
                "city", city,
                "days", days
        );
        Prompt prompt = template.create(params);

        return chatClient.prompt(prompt)
                .call().content();
    }

}
