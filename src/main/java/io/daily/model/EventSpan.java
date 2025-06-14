package io.daily.model;

import java.time.LocalTime;

public record EventSpan(LocalTime startTime,
                        LocalTime endTime) implements Comparable<EventSpan> {
    @Override
    public String toString() {
        return "[" + startTime + "–" + endTime + "]";
    }

    @Override
    public int compareTo(EventSpan eventSpan) {
        int startComparison = this.startTime.compareTo(eventSpan.startTime);
        if (startComparison != 0) {
            return startComparison;
        }
        return this.endTime.compareTo(eventSpan.endTime);
    }
}

