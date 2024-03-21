package entities;

import enums.TypeEvent;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;

@Entity
public class TrackMeet extends Event {
    private List<Person> athletes;
    private Person winner;

    public TrackMeet() {
    }

    public TrackMeet(String title, LocalDate eventDate, String description, TypeEvent typeEvent, int maxParticipant, Location location, List<Person> athletes, Person winner) {
        super(title, eventDate, description, typeEvent, maxParticipant, location);
        this.athletes = athletes;
        this.winner = winner;
    }

    public List<Person> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Person> athletes) {
        this.athletes = athletes;
    }

    public Person getWinner() {
        return winner;
    }

    public void setWinner(Person winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "TrackMeet{" +
                "athletes=" + athletes +
                ", winner=" + winner +
                ", eventDate=" + eventDate +
                '}';
    }
}
