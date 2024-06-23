package com.controller.event;

import com.model.ModelLogin;
import com.model.ModelRegister;

public interface EventLoginController {

    public void login(ModelLogin data);

    public void register(ModelRegister data, EventMessageController message);
    
    public void goRegister();

    public void goLogin();
    
}
