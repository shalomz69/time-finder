package io.daily.repository;

import io.daily.model.Event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CalendarRepository {
    private final ConcurrentMap<String, CopyOnWriteArraySet<Event>> eventsByPerson
            = new ConcurrentHashMap<>();

    public void addEvent(Event e) {
        eventsByPerson
                .computeIfAbsent(e.getPersonName(), k -> new CopyOnWriteArraySet<>())
                .add(e);
    }

    public Set<Event> getEventsFor(String person) {
        return eventsByPerson
                .getOrDefault(person, new CopyOnWriteArraySet<>());
    }
}
