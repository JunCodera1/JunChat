package com.controller.event;

import com.model.ModelUserAccount;
import java.util.List;

public interface EventMenuLeftController {
    
    public void newUser(List<ModelUserAccount> users);

    public void userConnect(int userID);

    public void userDisconnect(int userID);
}
