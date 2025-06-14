package io.daily.model.converter;

import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverterTest {

    @Test
    public void testConvert() {
        final LocalTimeConverter converter = new LocalTimeConverter();
        final String timeString = "12:30";
        final LocalTime time = (LocalTime) converter.convert(timeString);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        assert time.format(formatter).equals(timeString);
    }
}
