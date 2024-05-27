package com.event;

import com.model.ModelReceiveMessage;
import com.model.ModelSendMessage;

public interface EventChat {
    public void sendMessage(ModelSendMessage data);
    
    public void receiveMessage(ModelReceiveMessage data);
}
