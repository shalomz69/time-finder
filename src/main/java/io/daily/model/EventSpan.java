package io.daily.model;

import java.time.LocalTime;

public record EventSpan(LocalTime startTime,
                        LocalTime endTime){
    @Override
    public String toString() {
        return "[" + startTime + "–" + endTime + "]";
    }
}

