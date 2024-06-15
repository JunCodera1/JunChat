package com.event;

import java.io.File;

public interface EventFileReceiver {


    public void onStartReceiving();

    public void onFinish(File file);

    public void onReceiving(double percentage);
}
