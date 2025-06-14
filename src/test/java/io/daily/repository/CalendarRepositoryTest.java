package io.daily.repository;

import io.daily.model.Event;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Set;

public class CalendarRepositoryTest {
    private static final String JOHN_DOE = "John Doe";
    private static final Event EVENT_1 = new Event(JOHN_DOE,
            "Lunch",
            LocalTime.parse("12:00"),
            LocalTime.parse("13:00"));
    private static final Event EVENT_2 = new Event(JOHN_DOE,
            "Daily",
            LocalTime.parse("18:00"),
            LocalTime.parse("19:00"));
    private static final Event EVENT_3 = new Event("John Crowley",
            "Meeting",
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00"));
    @Test
    public void testAddEvent() {
        CalendarRepository repository = new CalendarRepository();
        repository.addEvent(EVENT_1);
        repository.addEvent(EVENT_2);

        Set<Event> events = repository.getEventsFor("John Doe");
        assert events.contains(EVENT_1);
    }

    @Test
    public void testGetEventsFor() {
        CalendarRepository repository = new CalendarRepository();
        repository.addEvent(EVENT_1);
        repository.addEvent(EVENT_2);
        repository.addEvent(EVENT_3);



        Set<Event> events = repository.getEventsFor("John Doe");
        assert events.equals(Set.of(EVENT_1, EVENT_2));
    }
}
