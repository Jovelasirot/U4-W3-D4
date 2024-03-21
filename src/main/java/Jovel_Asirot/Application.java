package Jovel_Asirot;

import DAO.AttendanceDAO;
import DAO.EventDAO;
import DAO.LocationDAO;
import DAO.PersonDAO;
import com.github.javafaker.Faker;
import entities.*;
import enums.Gender;
import enums.Genre;
import enums.SateAttendance;
import enums.TypeEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("events_v3");

    public static void main(String[] args) {

        EntityManager eM = emf.createEntityManager();

        EventDAO eDAO = new EventDAO(eM);
        LocationDAO lDAO = new LocationDAO(eM);
        PersonDAO pDAO = new PersonDAO(eM);
        AttendanceDAO aDAO = new AttendanceDAO(eM);

//      People
        Supplier<Person> personSupplier = getPersonSupplier(aDAO.getAllAttendances());
        List<Person> peopleList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            peopleList.add(personSupplier.get());
        }
        peopleList.forEach(pDAO::save);

//      Location
        Supplier<Location> locationSupplier = getLocationSupplier();
        List<Location> locationsList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            locationsList.add(locationSupplier.get());
        }
        locationsList.forEach(lDAO::save);

        List<Location> allLocations = lDAO.getAllLocations();


//        Events
        Supplier<Event> eventSupplier = getEventSupplier(allLocations);
        List<Event> eventList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            eventList.add(eventSupplier.get());
        }
        eventList.forEach(eDAO::save);


//        Attendance
        Supplier<Attendance> attendanceSupplier = getAttendanceSupplier(peopleList, eventList);
        for (int i = 0; i < 50; i++) {
            Attendance attendance = attendanceSupplier.get();
            aDAO.save(attendance);
        }

//        Football matches
        Supplier<FootBallMatch> footBallMatchSupplier = getFootBallMatchSupplier(allLocations);
        List<FootBallMatch> footBallMatchesList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            footBallMatchesList.add(footBallMatchSupplier.get());
        }
        footBallMatchesList.forEach(eDAO::save);


//        Track Meet
        Supplier<TrackMeet> trackMeetSupplier = getTrackMeetSupplier(locationsList, peopleList);
        List<TrackMeet> trackMeetList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            trackMeetList.add(trackMeetSupplier.get());
        }
        trackMeetList.forEach(eDAO::save);

//        Concert
        Supplier<Concert> concertSupplier = getConcertSupplier(allLocations);
        List<Concert> concertList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            concertList.add(concertSupplier.get());
        }
        concertList.forEach(eDAO::save);


//        eDAO.findALlFootballMatches().forEach(System.out::println);

        emf.close();
        eM.close();
    }

    public static Supplier<Event> getEventSupplier(List<Location> allLocations) {
        Random rdm = new Random();
        Faker faker = new Faker();
        TypeEvent[] typeEvents = TypeEvent.values();

        return () -> {
            String title = faker.esports().event();

            LocalDate dateEvent = LocalDate.now().plusDays(rdm.nextInt(365));

            String description = faker.esports().game();

            int rdmTypeEvent = rdm.nextInt(typeEvents.length);
            TypeEvent typeEvent = typeEvents[rdmTypeEvent];

            int maxParticipant = rdm.nextInt(10, 50);

            Location location = allLocations.get(rdm.nextInt(allLocations.size()));

            return new Event(title, dateEvent, description, typeEvent, maxParticipant, location);
        };
    }

    public static Supplier<Person> getPersonSupplier(List<Attendance> eventListAttended) {
        Random rdm = new Random();
        Faker faker = new Faker();
        Gender[] genders = Gender.values();

        return () -> {

            String namePerson = faker.name().firstName();
            String surnamePerson = faker.name().lastName();

            String email = namePerson + surnamePerson + "@gmail.com";

            LocalDate date = LocalDate.parse("2002-01-01");
            LocalDate birthDate = date.plusDays(rdm.nextInt(730));

            int rdmGender = rdm.nextInt(genders.length);
            Gender gender = genders[rdmGender];

            List<Attendance> attendanceList = new ArrayList<>();
            int eventsAttended = rdm.nextInt(eventListAttended.size() + 1);
            for (int i = 0; i < eventsAttended; i++) {
                Attendance attendedEvent = eventListAttended.get(rdm.nextInt(eventListAttended.size()));
                Attendance attendance = new Attendance(attendedEvent.getPerson(), attendedEvent.getEvent(), SateAttendance.CONFIRMED);
                attendanceList.add(attendance);
            }

            return new Person(namePerson, surnamePerson, email, birthDate, gender, eventListAttended);
        };
    }

    public static Supplier<Attendance> getAttendanceSupplier(List<Person> people, List<Event> events) {
        Random rdm = new Random();
        SateAttendance[] sateAttendances = SateAttendance.values();

        return () -> {
            Person randomPerson = people.get(rdm.nextInt(people.size()));

            Event randomEvent = events.get(rdm.nextInt(events.size()));


            int rdmStateAttendance = rdm.nextInt(sateAttendances.length);
            SateAttendance sateAttendance = sateAttendances[rdmStateAttendance];

            return new Attendance(randomPerson, randomEvent, sateAttendance);
        };
    }

    public static Supplier<Location> getLocationSupplier() {
        Faker faker = new Faker();

        return () -> {
            String city = faker.address().city();
            String cityName = faker.address().cityName();

            return new Location(city, cityName);
        };
    }

    public static Supplier<FootBallMatch> getFootBallMatchSupplier(List<Location> allLocations) {
        Random rdm = new Random();
        Faker faker = new Faker();
        TypeEvent[] typeEvents = TypeEvent.values();

        return () -> {
            String hostTeam = faker.dragonBall().character() + " team";
            String guestTeam = faker.dragonBall().character() + " team";

            int hostTeamGoals = rdm.nextInt(1, 3);
            int guestTeamGoals = rdm.nextInt(1, 3);

            String winner;
            if (hostTeamGoals > guestTeamGoals) {
                winner = hostTeam;
            } else if (guestTeamGoals > hostTeamGoals) {
                winner = guestTeam;
            } else {
                winner = null;
            }

            String title = faker.address().city() + " stadium - Football match";

            LocalDate dateEvent = LocalDate.now().plusDays(rdm.nextInt(365));

            String description = "'" + hostTeam + "'" + " vs " + "'" + guestTeam + "'";

            int rdmTypeEvent = rdm.nextInt(typeEvents.length);
            TypeEvent typeEvent = typeEvents[rdmTypeEvent];

            int maxParticipant = rdm.nextInt(10, 50);

            Location location = allLocations.get(rdm.nextInt(allLocations.size()));

            return new FootBallMatch(title, dateEvent, description, typeEvent, maxParticipant, location, hostTeam, guestTeam, hostTeamGoals, guestTeamGoals, winner);
        };
    }

    public static Supplier<TrackMeet> getTrackMeetSupplier(List<Location> allLocations, List<Person> peopleList) {
        Random rdm = new Random();
        Faker faker = new Faker();
        TypeEvent[] typeEvents = TypeEvent.values();


        return () -> {
            String title = faker.dragonBall().character() + " Track Meet";

            LocalDate dateEvent = LocalDate.now().plusDays(rdm.nextInt(365));

            String description = title + " - The meet of the month";

            int rdmTypeEvent = rdm.nextInt(typeEvents.length);
            TypeEvent typeEvent = typeEvents[rdmTypeEvent];

            int maxParticipant = rdm.nextInt(10, 50);

            Location location = allLocations.get(rdm.nextInt(allLocations.size()));

            Person winner = peopleList.get(rdm.nextInt(peopleList.size()));

            return new TrackMeet(title, dateEvent, description, typeEvent, maxParticipant, location, peopleList, winner);
        };
    }

    public static Supplier<Concert> getConcertSupplier(List<Location> allLocations) {
        Random rdm = new Random();
        Faker faker = new Faker();
        TypeEvent[] typeEvents = TypeEvent.values();
        Genre[] concertGenres = Genre.values();

        return () -> {
            String title = faker.dragonBall().character() + " concert";

            LocalDate dateEvent = LocalDate.now().plusDays(rdm.nextInt(365));

            String description = title + " - The concert of the year";

            int rdmTypeEvent = rdm.nextInt(typeEvents.length);
            TypeEvent typeEvent = typeEvents[rdmTypeEvent];

            int maxParticipant = rdm.nextInt(10, 50);

            Location location = allLocations.get(rdm.nextInt(allLocations.size()));

            Genre concertGenre = concertGenres[rdm.nextInt(concertGenres.length)];

            Boolean onStreaming = faker.bool().bool();

            return new Concert(title, dateEvent, description, typeEvent, maxParticipant, location, concertGenre, onStreaming);
        };

    }


}
