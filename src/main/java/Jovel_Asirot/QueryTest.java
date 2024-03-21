package Jovel_Asirot;

import DAO.AttendanceDAO;
import DAO.EventDAO;
import DAO.LocationDAO;
import DAO.PersonDAO;
import enums.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class QueryTest {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("events_v3");

    public static void main(String[] args) {
        EntityManager eM = emf.createEntityManager();

        EventDAO eDAO = new EventDAO(eM);
        LocationDAO lDAO = new LocationDAO(eM);
        PersonDAO pDAO = new PersonDAO(eM);
        AttendanceDAO aDAO = new AttendanceDAO(eM);

        System.out.println("Concert not in streaming");
        eDAO.getStreamingConcert(false).forEach(System.out::println);

        System.out.println("-----------------------------");

        System.out.println("Concert with genre classic");
        eDAO.getConcertByGenre(Genre.CLASSIC).forEach(System.out::println);

        System.out.println("-----------------------------");

        System.out.println("Matches won at home");
        eDAO.getMatchesWonAtHome().forEach(System.out::println);

        System.out.println("-----------------------------");

        System.out.println("Matches won in transfer");
        eDAO.getMatchesWonInTransfer().forEach(System.out::println);

        System.out.println("-----------------------------");

        System.out.println("Matches draw");
        eDAO.getMatchesDraw().forEach(System.out::println);

        eM.close();
        emf.close();

    }
}
