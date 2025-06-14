package io.daily.service;

import io.daily.model.Event;
import io.daily.repository.CalendarRepository;
import io.daily.service.parser.FileParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalendarPopulatorTest {
    private static final String EVENTS_CSV = "src/test/resources/io/daily/events.csv";
    private static final String JOHN_DOE = "John Doe";
    private static final Event EVENT_1 = new Event(JOHN_DOE,
            "Lunch",
            LocalTime.parse("12:00"),
            LocalTime.parse("13:00"));
    private static final Event EVENT_2 = new Event(JOHN_DOE,
            "Daily",
            LocalTime.parse("18:00"),
            LocalTime.parse("19:00"));
    private static final Event EVENT_3 = new Event("Jane Crowley",
            "Meeting",
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00"));
    private static final Set<Event> JOHNS_EVENTS = Set.of(EVENT_1, EVENT_2);
    private static final Set<Event> JANES_EVENTS = Set.of(EVENT_3);
    @Mock
    private FileParser<Event> fileParser;
    @Mock
    private CalendarRepository repository;
    @InjectMocks
    private CalendarPopulator populator;

    @Before
    public void setUp() throws FileNotFoundException {
        when(fileParser.parse(EVENTS_CSV, Event.class))
                .thenReturn(Set.of(JANES_EVENTS, JOHNS_EVENTS).stream()
                        .flatMap(Set::stream)
                        .collect(Collectors.toList()));
    }

    @Test
    public void testPopulateEvents() throws FileNotFoundException {
        populator.populateEvents(EVENTS_CSV);
        verify(repository, times(3)).addEvent(any(Event.class));
        verify(repository, times(1)).addEvent(EVENT_1);
        verify(repository, times(1)).addEvent(EVENT_2);
        verify(repository, times(1)).addEvent(EVENT_3);
    }

    @Test
    public void testFileNotFound() {
        try {
            populator.populateEvents("non_existent_file.csv");
        } catch (Exception e) {
            assertEquals(FileNotFoundException.class, e.getClass());
        }
        verify(repository, never()).addEvent(any(Event.class));
    }
}