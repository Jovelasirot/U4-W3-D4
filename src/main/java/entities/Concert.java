package entities;

import enums.Genre;
import enums.TypeEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Entity
public class Concert extends Event {

    @Enumerated(EnumType.STRING)
    private Genre concertGenre;


    private Boolean onStreaming;

    public Concert() {
    }

    public Concert(String title, LocalDate eventDate, String description, TypeEvent typeEvent, int maxParticipant, Location location, Genre concertGenre, Boolean onStreaming) {
        super(title, eventDate, description, typeEvent, maxParticipant, location);
        this.concertGenre = concertGenre;
        this.onStreaming = onStreaming;
    }

    public Genre getConcertGenre() {
        return concertGenre;
    }

    public void setConcertGenre(Genre concertGenre) {
        this.concertGenre = concertGenre;
    }

    public Boolean getOnStreaming() {
        return onStreaming;
    }

    public void setOnStreaming(Boolean onStreaming) {
        this.onStreaming = onStreaming;
    }

    @Override
    public String toString() {
        return "Concert{" +
                "concertGenre=" + concertGenre +
                ", onStreaming=" + onStreaming +
                ", eventDate=" + eventDate +
                '}';
    }
}
