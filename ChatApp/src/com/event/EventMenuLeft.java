package com.event;

import com.model.ModelUserAccount;
import java.util.List;

public interface EventMenuLeft {
    
    public void newUser(List<ModelUserAccount> users);

    public void userConnect(int userID);

    public void userDisconnect(int userID);
}
