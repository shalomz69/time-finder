package io.daily;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.daily.model.Event;
import io.daily.repository.CalendarRepository;
import io.daily.service.CalendarPopulator;
import io.daily.service.CalendarSlotFinder;
import io.daily.service.parser.CsvParser;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class AppTest {
    private final static String CALENDAR_CSV = "src/test/resources/io/daily/test_calendar.csv";
    private final CsvParser<Event> csvParser = new CsvParser<>();
    private final CalendarRepository repository = new CalendarRepository();
    private final CalendarPopulator populator = new CalendarPopulator(csvParser, repository);

    @Test
    public void testFindAvailableSlots() throws Exception {
            populator.populateEvents(CALENDAR_CSV);
        final var slotFinder = new CalendarSlotFinder(repository);

        List<LocalTime> availableSlots =
                slotFinder.findAvailableSlots(List.of("Jack", "Alice"), Duration.ofMinutes(60));
        System.out.println(availableSlots);
        assertEquals(8, availableSlots.size());

        assert List.of(LocalTime.of(7, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(17, 0),
                LocalTime.of(18, 0)).containsAll(
                availableSlots);
    }
}
