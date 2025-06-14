package io.daily.service;

import io.daily.model.Event;
import io.daily.model.EventSpan;
import io.daily.repository.CalendarRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class CalendarSlotFinder {
    private final CalendarRepository calendarRepository;

    private static final LocalTime START = LocalTime.parse("07:00");
    private static final LocalTime END = LocalTime.parse("19:00");

    public CalendarSlotFinder(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    public List<LocalTime> findAvailableSlots(List<String> persons, Duration eventDuration) {
        if (persons == null || persons.isEmpty()) {
            throw new IllegalArgumentException("Must specify at least one existing person");
        }
        if (eventDuration == null || eventDuration.isNegative() || eventDuration.isZero()) {
            throw new IllegalArgumentException("Invalid duration");
        }
        final var eventsSpans =  buildEvents(persons);
        System.out.println("event spans: " + eventsSpans);
        return buildAvailableSlots(eventsSpans, eventDuration);
    }

    private Set<EventSpan> buildEvents(List<String> persons) {
        Set<EventSpan> spans =  persons.stream()
                .map(calendarRepository::getEventsFor)
                .peek(events -> System.out.println("Existing events: " + events))
                .flatMap(Collection::stream)
                .map(this::buildEventSpan)
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(EventSpan::startTime)
                                .thenComparing(EventSpan::endTime))));
        spans.add(addEndLimit());
        return Collections.unmodifiableSet(spans);
    }

    private EventSpan buildEventSpan(Event event) {
        return new EventSpan(event.getStartTime(), event.getEndTime());
    }

    private EventSpan addEndLimit() {
        return new EventSpan(END, END);
    }

    private List<LocalTime> buildAvailableSlots(Set<EventSpan> events, Duration eventDuration){
        final List<LocalTime> availableSlots = new ArrayList<>();
        LocalTime currentEnd = START;
        for (EventSpan eventSpan : events) {
            LocalTime start = eventSpan.startTime();
            if (currentEnd.getMinute() > 0) {
                currentEnd = currentEnd.plusHours(1).withMinute(0);
            }
            while(Duration.between(currentEnd, start).compareTo(eventDuration) >= 0) {
                availableSlots.add(currentEnd);
                currentEnd = currentEnd.plus(eventDuration);
            }
            if (eventSpan.endTime().isAfter(currentEnd)) {
                currentEnd = eventSpan.endTime();
            }
        }
        return availableSlots;
    }
}
