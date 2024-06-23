package com.controller.event;

import java.io.File;

public interface EventFileReceiverController {


    public void onStartReceiving();

    public void onFinish(File file);

    public void onReceiving(double percentage);
}
