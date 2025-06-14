package io.daily.service;

import io.daily.model.Event;
import io.daily.service.parser.FileParser;
import io.daily.repository.CalendarRepository;

import java.io.FileNotFoundException;

public class CalendarPopulator {
    private final FileParser<Event> fileParser;
    private final CalendarRepository calendarRepository;

    public CalendarPopulator(FileParser<Event> fileParser, CalendarRepository calendarRepository){
        this.fileParser = fileParser;
        this.calendarRepository = calendarRepository;
    }

    public void populateEvents(String fileName) throws FileNotFoundException {
        fileParser.parse(fileName, Event.class)
                .forEach(calendarRepository::addEvent);
    }
}
