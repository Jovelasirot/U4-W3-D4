package DAO;

import entities.Event;
import entities.FootBallMatch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EventDAO {
    private final EntityManager em;

    public EventDAO(EntityManager em) {
        this.em = em;
    }


    public void save(Event event) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(event);
        transaction.commit();
        System.out.println("The event " + event.getTitle() + " has been saved correctly");
    }

    public Event findById(long eventId) {
        Event event = em.find(Event.class, eventId);
        if (event == null) throw new EntityNotFoundException(String.valueOf(eventId));
        return event;
    }

    public List<Event> getAllEvents() {
        return em.createQuery("SELECT e FROM Event e", Event.class)
                .getResultList();
    }

    public void deleteById(long eventId) {

        Event eventFound = this.findById(eventId);

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        em.remove(eventFound);

        transaction.commit();
        System.out.println("Event with id: " + eventFound.getId() + " was successfully deleted");


    }

    public List<FootBallMatch> findALlFootballMatches() {
        TypedQuery<FootBallMatch> query = em.createQuery("SELECT f FROM FootBallMatch f", FootBallMatch.class);
        return query.getResultList();
    }
}
