package com.controller.event;

public class PublicEvent {

    private static PublicEvent instance;
    private EventMainController eventMain;
    private EventImageViewController eventImageView;
    private EventChatController eventChat;
    private EventLoginController eventLogin;
    private EventMenuLeftController eventMenuLeft;

    public static PublicEvent getInstance() {
        if (instance == null) {
            instance = new PublicEvent();
        }
        return instance;
    }

    private PublicEvent() {

    }

    public void addEventMain(EventMainController event) {
        this.eventMain = event;
    }

    public void addEventImageView(EventImageViewController event) {
        this.eventImageView = event;
    }

    public void addEventChat(EventChatController event) {
        this.eventChat = event;
    }

    public void addEventLogin(EventLoginController event) {
        this.eventLogin = event;
    }

    public void addEventMenuLeft(EventMenuLeftController event) {
        this.eventMenuLeft = event;
    }

    public EventMainController getEventMain() {
        return eventMain;
    }

    public EventImageViewController getEventImageView() {
        return eventImageView;
    }

    public EventChatController getEventChat() {
        return eventChat;
    }

    public EventLoginController getEventLogin() {
        return eventLogin;
    }

    public EventMenuLeftController getEventMenuLeft() {
        return eventMenuLeft;
    }
}
