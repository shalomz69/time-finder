package io.daily.service;

import io.daily.model.Event;
import io.daily.model.EventSpan;
import io.daily.repository.CalendarRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class CalendarSlotFinder {
    private static final LocalTime WORK_DAY_START = LocalTime.of(7, 0);
    private static final LocalTime WORK_DAY_END = LocalTime.of(19, 0);
    private static final EventSpan END_OF_DAY_SENTINEL = new EventSpan(WORK_DAY_END, WORK_DAY_END);
    private final CalendarRepository calendarRepository;
    private static final int SLOT_GRANULARITY_MINUTES = 15;
    private static final Duration MAX_DURATION = Duration.ofHours(3);
    private static final Duration MIN_DURATION = Duration.ofMinutes(5);

    public CalendarSlotFinder(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    public List<LocalTime> findAvailableSlots(List<String> persons, Duration eventDuration) {
        validateInput(persons, eventDuration);
        final Set<EventSpan> occupiedSpans = buildOccupiedEventSpans(persons);

        System.out.println("Occupied spans: " + occupiedSpans);
        return buildAvailableSlots(occupiedSpans, eventDuration);
    }

    private void validateInput(List<String> persons, Duration duration) {
        if (persons == null || persons.isEmpty()) {
            throw new IllegalArgumentException("At least one person must be specified");
        }
        if (duration == null || duration.compareTo(MIN_DURATION) < 0 || duration.compareTo(MAX_DURATION) > 0) {
            throw new IllegalArgumentException("Duration must be between 5 minutes and 3 hours");
        }
    }

    private Set<EventSpan> buildOccupiedEventSpans(List<String> persons) {
        return persons.stream()
                .map(calendarRepository::getEventsFor)
                .peek(events -> System.out.println("Existing events: " + events))
                .flatMap(Collection::stream)
                .map(this::toEventSpan)
                .collect(Collectors.toCollection(() -> {
                    TreeSet<EventSpan> sortedSpans = new TreeSet<>();
                    sortedSpans.add(END_OF_DAY_SENTINEL);
                    return sortedSpans;
                }));
    }

    private EventSpan toEventSpan(Event event) {
        return new EventSpan(event.getStartTime(), event.getEndTime());
    }

    private List<LocalTime> buildAvailableSlots(Set<EventSpan> events, Duration eventDuration) {
        final List<LocalTime> availableSlots = new ArrayList<>();
        LocalTime currentTime = WORK_DAY_START;

        for (EventSpan eventSpan : events) {
            LocalTime nextBusyStart = eventSpan.startTime();
            currentTime = alignToNextGranularity(currentTime);

            while (!currentTime.plus(eventDuration).isAfter(nextBusyStart)) {
                availableSlots.add(currentTime);
                currentTime = currentTime.plusMinutes(SLOT_GRANULARITY_MINUTES);
            }
            if (eventSpan.endTime().isAfter(currentTime)) {
                currentTime = eventSpan.endTime();
            }
        }
        return availableSlots;
    }

    private LocalTime alignToNextGranularity(LocalTime time) {
        final int minute = time.getMinute();
        final int remainder = minute % SLOT_GRANULARITY_MINUTES;
        if (remainder == 0) return time;
        return time.plusMinutes(SLOT_GRANULARITY_MINUTES - remainder);
    }
}

