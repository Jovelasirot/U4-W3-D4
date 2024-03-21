package Jovel_Asirot;

import DAO.AttendanceDAO;
import DAO.EventDAO;
import DAO.LocationDAO;
import DAO.PersonDAO;
import com.github.javafaker.Faker;
import entities.Attendance;
import entities.Event;
import entities.Location;
import entities.Person;
import enums.Gender;
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
        for (int i = 0; i < 20; i++) {
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
        for (int i = 0; i < 30; i++) {
            eventList.add(eventSupplier.get());
        }

        eventList.forEach(eDAO::save);


        for (Event event : eventList) {
            int randomIndex = new Random().nextInt(allLocations.size());
            Location randomLocation = allLocations.get(randomIndex);
            event.setLocation(randomLocation);
        }


//        Attendance
        Supplier<Attendance> attendanceSupplier = getAttendanceSupplier(peopleList, eventList);
        for (int i = 0; i < 50; i++) {
            Attendance attendance = attendanceSupplier.get();
            aDAO.save(attendance);
        }


        emf.close();
        eM.close();
    }

    public static Supplier<Event> getEventSupplier(List<Location> allLocations) {
        Random rdm = new Random();
        Faker faker = new Faker();
        TypeEvent[] typeEvents = TypeEvent.values();

        return () -> {
            String title = faker.esports().event();
            String description = faker.esports().game();

            LocalDate dateEvent = LocalDate.now().plusDays(rdm.nextInt(365));

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

    public static Supplier<Location> getLocationSupplier(){
        Faker faker = new Faker();
        return ()->{
            String city = faker.address().city();
            String cityName = faker.address().cityName();

            return new Location(city,cityName);
        };
    }
}
