package io.daily;

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

        final List<LocalTime> availableSlots =
                slotFinder.findAvailableSlots(List.of("Jack", "Alice"), Duration.ofMinutes(90));

        assert availableSlots.size() == 14 : "Expected 14 available slots, but found " + availableSlots.size();
        assert availableSlots.get(0).equals(LocalTime.of(9, 45)) : "First slot should be 09:45";
        assert availableSlots.get(1).equals(LocalTime.of(10, 0)) : "Second slot should be 10:00";
        assert availableSlots.get(2).equals(LocalTime.of(10, 15)) : "Third slot should be 10:15";
        assert availableSlots.get(3).equals(LocalTime.of(10, 30)) : "Fourth slot should be 10:30";
        assert availableSlots.get(4).equals(LocalTime.of(10, 45)) : "Fifth slot should be 10:45";
        assert availableSlots.get(5).equals(LocalTime.of(11, 0)) : "Sixth slot should be 11:00";
        assert availableSlots.get(6).equals(LocalTime.of(11, 15)) : "Seventh slot should be 11:15";
        assert availableSlots.get(7).equals(LocalTime.of(11, 30)) : "Eighth slot should be 11:30";
        assert availableSlots.get(8).equals(LocalTime.of(14, 0)) : "Ninth slot should be 14:00";
        assert availableSlots.get(9).equals(LocalTime.of(14, 15)) : "Tenth slot should be 14:15";
        assert availableSlots.get(10).equals(LocalTime.of(14, 30)) : "Eleventh slot should be 14:30";
        assert availableSlots.get(11).equals(LocalTime.of(17, 0)) : "Twelfth slot should be 17:00";
        assert availableSlots.get(12).equals(LocalTime.of(17, 15)) : "Thirteenth slot should be 17:15";
        assert availableSlots.get(13).equals(LocalTime.of(17, 30)) : "Fourteenth slot should be 17:30";
        System.out.println("All tests passed. Available slots found successfully.");
    }
}
