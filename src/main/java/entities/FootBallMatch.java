package entities;

import enums.TypeEvent;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class FootBallMatch extends Event {

    private String hostTeam;

    private String guestTeam;


    private int hostTeamGoals;

    private int guestTeamGoals;


    private String winnerFootBallMatch;

    public FootBallMatch() {
    }

    public FootBallMatch(String title, LocalDate eventDate, String description, TypeEvent typeEvent, int maxParticipant, Location location, String hostTeam, String guestTeam, int hostTeamGoals, int guestTeamGoals, String winner) {
        super(title, eventDate, description, typeEvent, maxParticipant, location);
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.winnerFootBallMatch = winner;
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
        return winnerFootBallMatch;
    }

    public void setWinner(String winner) {
        this.winnerFootBallMatch = winner;
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
                ", winner='" + winnerFootBallMatch + '\'' +
                ", hostTeamGoals=" + hostTeamGoals +
                ", guestTeamGoals=" + guestTeamGoals +
                ", eventDate=" + eventDate +
                '}';
    }
}
