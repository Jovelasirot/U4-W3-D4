package entities;

import enums.TypeEvent;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class FootBallMatch extends Event {
    private String hostTeam;
    private String guestTeam;

    private String winner;
    private int hostTeamGoals;
    private int guestTeamGoals;

    public FootBallMatch() {
    }

    public FootBallMatch(String title, LocalDate eventDate, String description, TypeEvent typeEvent, int maxParticipant, Location location, String hostTeam, String guestTeam, String winner, int hostTeamGoals, int guestTeamGoals) {
        super(title, eventDate, description, typeEvent, maxParticipant, location);
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.winner = winner;
        this.hostTeamGoals = hostTeamGoals;
        this.guestTeamGoals = guestTeamGoals;
    }

    public String getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(String guestTeam) {
        this.guestTeam = guestTeam;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getHostTeamGoals() {
        return hostTeamGoals;
    }

    public void setHostTeamGoals(int hostTeamGoals) {
        this.hostTeamGoals = hostTeamGoals;
    }

    public int getGuestTeamGoals() {
        return guestTeamGoals;
    }

    public void setGuestTeamGoals(int guestTeamGoals) {
        this.guestTeamGoals = guestTeamGoals;
    }

    @Override
    public String toString() {
        return "FootBallMatch{" +
                "hostTeam='" + hostTeam + '\'' +
                ", guestTeam='" + guestTeam + '\'' +
                ", winner='" + winner + '\'' +
                ", hostTeamGoals=" + hostTeamGoals +
                ", guestTeamGoals=" + guestTeamGoals +
                ", eventDate=" + eventDate +
                '}';
    }
}
