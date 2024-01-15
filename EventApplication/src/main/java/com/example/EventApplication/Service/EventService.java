package com.example.EventApplication.Service;

import com.example.EventApplication.Enum.SortType;
import com.example.EventApplication.model.Event;
import com.example.EventApplication.model.request.EventBuilder;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> getAllEvents(String pattern, String sortType);

    Optional<Event> getEventById(long id);

    Event addEvent(EventBuilder eventBuilder);

    Event updateEvent(EventBuilder eventBuilder);

    Long deleteById(long id);

    List<Event> addEvents(List<EventBuilder> eventList);

    List<Long> deleteByIds(List<Long> ids);

    List<Event> updateEvents(List<EventBuilder> eventList);

    List<Long> subscribeEvents(Long participantId, List<Long> eventIds);

    List<Long> unsubscribeEvents(Long participantId, List<Long> eventIds);
}
