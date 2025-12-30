package com.infiproton.springaidemo.model;

import lombok.Data;

@Data
public class ChatRequest {
    private String conversationId;
    private String message;
    private String imageName;
}
