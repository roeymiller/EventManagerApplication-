package com.example.EventApplication.Component;

import com.example.EventApplication.Enum.SortType;
import com.example.EventApplication.model.Event;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class SortingComponent {
    public List<Event> getSortedEvents(List<Event> events, String sortType) {
        var type = Optional.ofNullable(sortType)
                .map(s -> {
                    try {
                        return SortType.valueOf(s);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .orElse(null);
        if(type!=null){
            switch (type) {
                case DATE:
                    getSortedEventsByDate(events);
                    break;
                case POPULARITY:
                    getSortedEventsByPopularity(events);
                    break;
                case CREATION_TIME:
                    getSortedEventsByCreationTime(events);
                    break;
            }
        }
        return events;
    }

    public List<Event> getSortedEventsByDate(List<Event> events) {
        Collections.sort(events, (event1, event2) -> event1.getDateTime().compareTo(event2.getDateTime()));
        return events;
    }

    public List<Event> getSortedEventsByPopularity(List<Event> events) {
        Collections.sort(events, Comparator.comparing(Event::getNumberOfParticipants).reversed());
        return events;
    }

    public List<Event> getSortedEventsByCreationTime(List<Event> events) {
        Collections.sort(events, Comparator.comparing(Event::getCreationDate));
        return events;
    }
}
