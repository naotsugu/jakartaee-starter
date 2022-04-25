package com.mammb.jakartaee.starter.app.example.cdievent;

import com.mammb.jakartaee.starter.domail.example.cdievent.CdiEventLog;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Transactional
@ApplicationScoped
public class CdiEventObserver {

    private static final Logger log = Logger.getLogger(CdiEventObserver.class.getName());

    @Inject
    private EntityManager em;

    public void onEvent(@Observes MessageEvent event) {
        em.persist(new CdiEventLog(
            event.source, event.occurredOn,
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            LocalDateTime.now(),
            event.text));
    }

    public void onEventAsync(@ObservesAsync MessageEvent event) {
        // Async call needs @Transactional
        em.persist(new CdiEventLog(
            event.source, event.occurredOn,
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            LocalDateTime.now(),
            event.text));
    }

    public void onEventPriorityFirst(@Observes @Priority(1) MessageEvent event) {
        em.persist(new CdiEventLog(
            event.source, event.occurredOn,
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            LocalDateTime.now(),
            event.text));
    }

    public void onEventPrioritySecond(@Observes @Priority(2) MessageEvent event) {
        em.persist(new CdiEventLog(
            event.source, event.occurredOn,
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            LocalDateTime.now(),
            event.text));
    }

    public void onEventBeforeCompletion(@Observes(during = TransactionPhase.BEFORE_COMPLETION) MessageEvent event) {
        em.persist(new CdiEventLog(
            event.source, event.occurredOn,
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            LocalDateTime.now(),
            event.text));

    }

    public void onEventAfterCompletion(@Observes(during = TransactionPhase.AFTER_COMPLETION) MessageEvent event) {
        // out of transaction
        log.info("#### ******************************************************");
        log.info("#### onEventAfterCompletion [" + event.text + "] " + Thread.currentThread().getStackTrace()[1].getMethodName());
        log.info("#### ******************************************************");
    }

    public void onEventAfterSuccess(@Observes(during = TransactionPhase.AFTER_SUCCESS) MessageEvent event) {
        // out of transaction
        log.info("#### ******************************************************");
        log.info("#### onEventAfterSuccess [" + event.text + "] " + Thread.currentThread().getStackTrace()[1].getMethodName());
        log.info("#### ******************************************************");
    }

    public void onEventAfterFailure(@Observes(during = TransactionPhase.AFTER_FAILURE) MessageEvent event) {
        // out of transaction
        log.info("#### ******************************************************");
        log.info("#### onEventAfterFailure [" + event.text + "] " + Thread.currentThread().getStackTrace()[1].getMethodName());
        log.info("#### ******************************************************");
    }

}
