package io.daily.service.parser;

import io.daily.model.Event;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class CsvParserTest {
    public static final String MALFORMED_EVENTS_CSV = "src/test/resources/io/daily/malformed_events.csv";
    private static final String EVENTS_CSV = "src/test/resources/io/daily/events.csv";

    @Test
    public void testParse() throws FileNotFoundException {
        final CsvParser<Event> csvParser = new CsvParser<>();
        final Class<Event> type = Event.class;
        final List<Event> events = csvParser.parse(EVENTS_CSV, type);

        assert events != null;
        assert events.size() == 3 : "Expected 3 events, but found " + events.size();
        for (Event event : events) {
            assert event != null : "Event should not be null";

        }
    }

    @Test(expected = RuntimeException.class)
    public void testMalformed() throws FileNotFoundException {
        final CsvParser<Event> csvParser = new CsvParser<>();
        final Class<Event> type = Event.class;
        csvParser.parse(MALFORMED_EVENTS_CSV, type);
    }
}
