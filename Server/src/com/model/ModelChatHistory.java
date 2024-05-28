package com.model;

import java.util.ArrayList;
import java.util.List;

public class ModelChatHistory {

    private List<ModelMessage> messages;

    public ModelChatHistory() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(ModelMessage message) {
        messages.add(message);
    }

    public List<ModelMessage> getMessages() {
        return messages;
    }

    // Additional methods for managing the chat history
}
