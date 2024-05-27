package com.event;

import com.model.ModelUserAccount;

public interface EventMain {

    public void showLoading(boolean show);

    public void initChat();
    
    public void selectUser(ModelUserAccount user);
    
    public void updateUser(ModelUserAccount user);
}
