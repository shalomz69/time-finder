package io.daily.service;

import io.daily.model.Event;
import io.daily.repository.CalendarRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalenderSlotFinderTest {
    @Mock
    private CalendarRepository repository;
    @InjectMocks
    private CalendarSlotFinder slotFinder;

    @Before
    public void setUp() {
        when(repository.getEventsFor("Jack")).thenReturn(new HashSet<>(Arrays.asList(
                new Event("Jack", "Daily", LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new Event("Jack", "Daily", LocalTime.of(11, 0), LocalTime.of(12, 0))
        )));

        when(repository.getEventsFor("Alice")).thenReturn(new HashSet<>(Arrays.asList(
                new Event("Alice", "Daily", LocalTime.of(10, 30), LocalTime.of(11, 30)),
                new Event("Alice", "Daily", LocalTime.of(12, 30), LocalTime.of(13, 30))
        )));
    }

    @Test
    public void testFindAvailableSlots() {
        setUp();
        List<LocalTime> availableSlots = slotFinder.findAvailableSlots(List.of("Jack", "Alice"), Duration.ofMinutes(120));
        List<LocalTime> expectedSlots = Arrays.asList(
                LocalTime.of(7, 0),
                LocalTime.of(14, 0),
                LocalTime.of(16, 0));;

        assert availableSlots.size() == expectedSlots.size();
        assert expectedSlots.containsAll(availableSlots);
    }
}
