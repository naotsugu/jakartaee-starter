package com.mammb.jakartaee.starter.app.example.cdievent;

import com.mammb.jakartaee.starter.domail.example.cdievent.CdiEventLog;
import com.mammb.jakartaee.starter.domail.example.cdievent.CdiEventLog_;
import com.mammb.jakartaee.starter.domail.example.crud.Project_;
import com.mammb.jakartaee.starter.domail.example.ejb.Customer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@RequestScoped
public class CdiEventService {

    @Inject
    private Event<MessageEvent> event;

    @Inject
    private EntityManager em;


    public void fireEvent(String msg) {
        event.fire(new MessageEvent(this.getClass().getSimpleName() + " @fire", LocalDateTime.now(), msg));
        event.fireAsync(new MessageEvent(this.getClass().getSimpleName() + " @fireAsync", LocalDateTime.now(), msg));
    }

    public List<CdiEventLog> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CdiEventLog> query = cb.createQuery(CdiEventLog.class);
        Root<CdiEventLog> log = query.from(CdiEventLog.class);
        query.select(log).orderBy(cb.desc(log.get(CdiEventLog_.id)));
        return em.createQuery(query).getResultList();
    }

    public void deleteAll() {
        findAll().stream().forEach(em::remove);
    }

}
