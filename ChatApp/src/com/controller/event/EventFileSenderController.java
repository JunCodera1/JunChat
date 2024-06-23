package com.controller.event;

public interface EventFileSenderController {

    public void onSending(double percentage);

    public void onStartSending();

    public void onFinish();
}
