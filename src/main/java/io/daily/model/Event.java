package io.daily.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import io.daily.model.converter.LocalTimeConverter;

import java.time.LocalTime;
import java.util.Objects;

public class Event {
    @CsvBindByPosition(position = 0)
    private String personName;
    @CsvBindByPosition(position = 1)
    private String subject;
    @CsvBindByPosition(position = 2)
    @CsvCustomBindByPosition(position = 2, converter = LocalTimeConverter.class)
    private LocalTime startTime;
    @CsvBindByPosition(position = 3)
    @CsvCustomBindByPosition(position = 3, converter = LocalTimeConverter.class)
    private LocalTime endTime;

    public Event() {
        // Default constructor for CSV parsing
    }

    public Event(String personName, String subject, LocalTime startTime, LocalTime endTime) {
        this.personName = personName;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getPersonName() {
        return personName;
    }

    public String getSubject() {
        return subject;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "[" + personName + ": " + startTime + "–" + endTime + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(personName, event.personName) && Objects.equals(subject, event.subject) && Objects.equals(startTime, event.startTime) && Objects.equals(endTime, event.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personName, subject, startTime, endTime);
    }
}


