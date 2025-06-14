package io.daily;

import io.daily.model.Event;
import io.daily.service.CalendarPopulator;
import io.daily.service.CalendarSlotFinder;
import io.daily.service.parser.CsvParser;
import io.daily.repository.CalendarRepository;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.List;

public class App {
    private final static String CALENDAR_CSV = "./src/main/resources/io/daily/calendar.csv";

    public static void main(String[] args) {
        final var csvParser = new CsvParser<Event>();
        final var repository = new CalendarRepository();
        final var populator = new CalendarPopulator(csvParser, repository);
        final var slotFinder = new CalendarSlotFinder(repository);

        final List<String> persons = List.of("Jack", "Alice");
        final Duration meetingDuration = Duration.ofMinutes(60);

        try {
            populator.populateEvents(CALENDAR_CSV);
            System.out.println("Calendar populated successfully from " + CALENDAR_CSV);
            System.out.println("Finding slots for " + persons +
                    " (duration: " + meetingDuration.toMinutes() + " mins)");
            slotFinder.findAvailableSlots(persons, meetingDuration)
                    .forEach(slot -> System.out.println("Available slot: " + slot));

        } catch (FileNotFoundException ex) {
            System.err.println("Failed to load calendar: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.err.println("Input error: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("An error occurred while processing the calendar: " + ex.getMessage());
        }
    }

}
