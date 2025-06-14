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

import static org.junit.Assert.assertThrows;
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
        List<LocalTime> availableSlots = slotFinder.findAvailableSlots(List.of("Jack", "Alice"), Duration.ofMinutes(180));
        List<LocalTime> expectedSlots = Arrays.asList(
                (LocalTime.of(13, 30)),
                LocalTime.of(13, 45),
                LocalTime.of(14, 0),
                LocalTime.of(14, 15),
                LocalTime.of(14, 30),
                LocalTime.of(14, 45),
                LocalTime.of(15, 0),
                LocalTime.of(15, 15),
                LocalTime.of(15, 30),
                LocalTime.of(15, 45),
                LocalTime.of(16, 0));
        assert availableSlots.size() == expectedSlots.size();
        assert expectedSlots.containsAll(availableSlots);
    }

    @Test
    public void shouldThrowWhenPersonsIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                slotFinder.findAvailableSlots(null, Duration.ofMinutes(60))
        );
    }

    @Test
    public void shouldThrowWhenPersonsIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                slotFinder.findAvailableSlots(Collections.emptyList(), Duration.ofMinutes(60))
        );
    }

    @Test
    public void shouldThrowWhenDurationIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                slotFinder.findAvailableSlots(List.of("Alice"), null)
        );
    }

    @Test
    public void shouldThrowWhenDurationIsUnderFiveMinutes() {
        assertThrows(IllegalArgumentException.class, () ->
                slotFinder.findAvailableSlots(List.of("Alice"), Duration.ofMinutes(3))
        );
    }

    @Test
    public void shouldThrowWhenDurationIsOverThreeHours() {
        assertThrows(IllegalArgumentException.class, () ->
                slotFinder.findAvailableSlots(List.of("Alice"), Duration.ofMinutes(181))
        );
    }
}

