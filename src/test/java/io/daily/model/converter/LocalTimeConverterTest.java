package io.daily.model.converter;

import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverterTest {

    @Test
    public void testConvert() {
        LocalTimeConverter converter = new LocalTimeConverter();
        String timeString = "12:30";
        LocalTime time = (LocalTime) converter.convert(timeString);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        assert time.format(formatter).equals(timeString);
    }
}
