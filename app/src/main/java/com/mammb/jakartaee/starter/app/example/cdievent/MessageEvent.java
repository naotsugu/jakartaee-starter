package com.mammb.jakartaee.starter.app.example.cdievent;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageEvent implements Serializable {

    public String source;
    public LocalDateTime occurredOn;
    public String text;

    public MessageEvent(String source, LocalDateTime occurredOn, String text) {
        this.source = source;
        this.occurredOn = occurredOn;
        this.text = text;
    }
}
