package com.controller.event;

import com.model.ModelUserAccount;

public interface EventMainController {

    public void showLoading(boolean show);

    public void initChat();
    
    public void selectUser(ModelUserAccount user);
    
    public void updateUser(ModelUserAccount user);
}
