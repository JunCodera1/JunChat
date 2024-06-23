package com.controller.event;

import com.model.ModelReceiveMessage;
import com.model.ModelSendMessage;

public interface EventChatController {
    public void sendMessage(ModelSendMessage data);
    
    public void receiveMessage(ModelReceiveMessage data);
}
