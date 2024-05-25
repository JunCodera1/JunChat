package com.event;

import com.model.ModelRegister;

public interface EventLogin {

    public void login();

    public void register(ModelRegister data, EventMessage message);

    public void goRegister();

    public void goLogin();
}
