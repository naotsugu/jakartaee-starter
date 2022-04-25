package com.mammb.jakartaee.starter.domail.example.cdievent;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity(name = CdiEventLog.NAME)
public class CdiEventLog extends BaseEntity<CdiEventLog> {

    public static final String NAME = "CDI_EVENT_LOGS";

    private String source;
    public LocalDateTime occurredOn;
    private String observed;
    public LocalDateTime observedOn;
    private String message;

    public CdiEventLog() {
    }

    public CdiEventLog(String source, LocalDateTime occurredOn, String observed, LocalDateTime observedOn, String message) {
        this.source = source;
        this.occurredOn = occurredOn;
        this.observed = observed;
        this.observedOn = observedOn;
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    public String getObserved() {
        return observed;
    }

    public LocalDateTime getObservedOn() {
        return observedOn;
    }

    public String getMessage() {
        return message;
    }
}
