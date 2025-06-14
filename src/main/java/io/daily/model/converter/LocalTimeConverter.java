package io.daily.model.converter;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter extends AbstractBeanField<LocalTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    protected Object convert(String value) {
        return LocalTime.parse(value.trim(), FORMATTER);
    }
}
