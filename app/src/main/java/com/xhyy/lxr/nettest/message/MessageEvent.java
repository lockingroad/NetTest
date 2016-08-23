package com.xhyy.lxr.nettest.message;

/**
 * Created by LXR on 2016/8/18.
 */
public class MessageEvent {
    public String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
