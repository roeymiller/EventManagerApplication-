package com.example.EventApplication.Service;

import com.example.EventApplication.Component.SortingComponent;
import com.example.EventApplication.Enum.SortType;
import com.example.EventApplication.Exception.NotFoundException;
import com.example.EventApplication.model.Event;
import com.example.EventApplication.model.User;
import com.example.EventApplication.model.request.EventBuilder;
import com.example.EventApplication.repository.EventRepository;
import com.example.EventApplication.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SortingComponent sortingComponent;

    @Override
    public List<Event> getAllEvents(String pattern, String sortType) {
        if(pattern==null)
            return sortingComponent.getSortedEvents(eventRepository.findAll(),sortType);
        return sortingComponent.getSortedEvents(eventRepository.findByLocationOrVenueLike(pattern),sortType);
    }

    @Override
    public Optional<Event> getEventById(long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event addEvent(EventBuilder eventBuilder) {
        var newEvent = modelMapper.map(eventBuilder, Event.class);
        if (eventBuilder.getOrganizerId() != null) {
            Optional<User> optionalOrganizer = userRepository.findById(eventBuilder.getOrganizerId());
            if (optionalOrganizer.isPresent()) {
                newEvent.setOrganizerId(optionalOrganizer.map(User::getId)
                        .orElse(null));
            } else {
                throw new NotFoundException("Organizer with ID " + eventBuilder.getOrganizerId() + " not found");
            }
        }
        newEvent.setCreationDate(LocalDateTime.now());
        return eventRepository.save(newEvent);
    }

    @Override
    public Event updateEvent(EventBuilder eventBuilder) {
        var id = Optional.ofNullable(eventBuilder)
                .map(EventBuilder::getId)
                .orElse(null);
        if (id == null) {
            throw new NotFoundException("Event with ID " + id + " not found");
        }
        var optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            var updatedEvent = optionalEvent.get();
            updatedEvent.setDescription(eventBuilder.getDescription());
            updatedEvent.setDateTime(eventBuilder.getDateTime());
            updatedEvent.setLocation(eventBuilder.getLocation());
            updatedEvent.setDurationInMinutes(eventBuilder.getDurationInMinutes());
            updatedEvent.setVenue(eventBuilder.getVenue());
            updatedEvent.setTitle(eventBuilder.getTitle());
            if (eventBuilder.getOrganizerId() != null) {
                Optional<User> optionalOrganizer = userRepository.findById(eventBuilder.getOrganizerId());
                if (optionalOrganizer.isPresent()) {
                    updatedEvent.setOrganizerId(optionalOrganizer.map(User::getId)
                            .orElse(null));
                } else {
                    throw new NotFoundException("Organizer with ID " + eventBuilder.getOrganizerId() + " not found");
                }
            }
            if (eventBuilder.getSubscriberIds() != null) {
                Set<User> subscribers = new HashSet<>();
                for (Long subscriberId : eventBuilder.getSubscriberIds()) {
                    Optional<User> optionalSubscriber = userRepository.findById(subscriberId);
                    if (optionalSubscriber.isPresent()) {
                        subscribers.add(optionalSubscriber.get());
                    } else {
                        throw new NotFoundException("Subscriber with ID " + subscriberId + " not found");
                    }
                }
                updatedEvent.setSubscriberIds(subscribers.stream()
                        .map(User::getId)
                        .collect(Collectors.toSet()));
                updatedEvent.setNumberOfParticipants(Optional.ofNullable(eventBuilder)
                        .map(EventBuilder::getSubscriberIds)
                        .map(Set::size)
                        .orElse(0));
            }
            return eventRepository.save(updatedEvent);
        } else {
            throw new NotFoundException("Event with ID " + id + " not found");
        }
    }

    @Override
    public Long deleteById(long id) {
        eventRepository.deleteById(id);
        return id;
    }

    @Override
    public List<Event> addEvents(List<EventBuilder> eventList) {
        return eventList.stream()
                .map(eventBuilder -> addEvent(eventBuilder))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> deleteByIds(List<Long> ids) {
        return ids.stream().map(id -> deleteById(id)).collect(Collectors.toList());
    }

    @Override
    public List<Event> updateEvents(List<EventBuilder> eventList) {
        return eventList.stream()
                .map(eventBuilder -> updateEvent(eventBuilder))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> subscribeEvents(Long participantId, List<Long> eventIds) {
        var events = eventRepository.findAllById(eventIds);
        events = events.stream()
                .map(event -> addSubscriberToEvent(participantId, event))
                .collect(Collectors.toList());
        eventRepository.saveAll(events);
        return events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> unsubscribeEvents(Long participantId, List<Long> eventIds) {
        var events = eventRepository.findAllById(eventIds);
        events = events.stream()
                .map(event -> removeSubscriberFromEvent(participantId, event))
                .collect(Collectors.toList());
        eventRepository.saveAll(events);
        return events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
    }

    private Event removeSubscriberFromEvent(Long participantId, Event event) {
        var participant = userRepository.findById(participantId)
                .orElse(null);
        if (participant == null)
            throw new NotFoundException("User with ID " + participantId + " not found");
        var subscribersList = Optional.ofNullable(event)
                .map(Event::getSubscriberIds)
                .orElse(new HashSet<>());
        if(subscribersList.contains(participantId)) {
            subscribersList.remove(participantId);
            var numOfParticipants = Optional.ofNullable(event)
                    .map(Event::getNumberOfParticipants)
                    .orElse(0);
            if (numOfParticipants != 0)
                numOfParticipants -= 1;
            event.setSubscriberIds(subscribersList);
            event.setNumberOfParticipants(numOfParticipants);
        }
        return event;
    }

    private Event addSubscriberToEvent(Long participantId, Event event) {
        var participant = userRepository.findById(participantId)
                .orElse(null);
        if (participant == null)
            throw new NotFoundException("User with ID " + participantId + " not found");
        var subscribersList = Optional.ofNullable(event)
                .map(Event::getSubscriberIds)
                .orElse(new HashSet<>());
        if(!subscribersList.contains(participantId)){
            subscribersList.add(participantId);
            var numOfParticipants = Optional.ofNullable(event)
                    .map(Event::getNumberOfParticipants)
                    .orElse(0);
            numOfParticipants += 1;
            event.setSubscriberIds(subscribersList);
            event.setNumberOfParticipants(numOfParticipants);
        }
        return event;
    }
}
