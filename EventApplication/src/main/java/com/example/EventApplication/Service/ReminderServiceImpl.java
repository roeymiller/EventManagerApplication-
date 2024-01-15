package com.example.EventApplication.Service;

import com.example.EventApplication.Enum.SortType;
import com.example.EventApplication.model.Event;
import com.example.EventApplication.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
public class ReminderServiceImpl implements ReminderService{

    @Autowired
    private EventService eventService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 60000) // Run every minute
    public void sendReminders() {
        List<Event> events = eventService.getAllEvents(null, SortType.DATE.name());
        LocalDateTime now = LocalDateTime.now();

        for (Event event : events) {
            LocalDateTime eventTime = event.getDateTime();
            Duration timeUntilEvent = Duration.between(now, eventTime);
            if (timeUntilEvent.toMinutes() >= 30 && timeUntilEvent.toMinutes() < 31) {
                sendReminder(event);
            }
        }
    }

    private void sendReminder(Event event) {
        var message = new SimpleMailMessage();
        var subscribers = Optional.ofNullable(event)
                .map(Event::getSubscriberIds)
                .map(subscribersList -> userService.getUsersById(subscribersList))
                .orElse(Collections.emptyList());
        var subscribersEmails = subscribers.stream()
                        .map(User::getEmail)
                        .toArray(String[]::new);
        var subscribersUserNames = subscribers.stream()
                .map(User::getUserName)
                .toArray(String[]::new);
        message.setTo(subscribersEmails);
        message.setSubject("Reminder: Upcoming Event - " + event.getVenue() + "at " +event.getLocation());
        message.setText("Dear Attendee"+ Arrays.toString(subscribersUserNames) +",\n\nThis is a reminder for the upcoming event: " + event.getVenue() + event.getLocation() +
                " scheduled at " + event.getDateTime() + ".\n\nPlease be on time and don't miss it!\n\nBest regards,\nYour Event Manager");
        log.info(message.toString());
        emailService.sendEmail(message);
    }
}