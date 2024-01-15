package com.example.EventApplication.Controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/websocket")
public class WebSocketController {

    @MessageMapping("/subscribe-to-event/{eventId}")
    public void subscribeToEvent(@DestinationVariable Long eventId, Principal principal) {
        // Add logic to subscribe the user to the event
        // Store the subscription information in the database
    }

    @Scheduled(fixedRate = 1800000) // 30 minutes in milliseconds
    public void sendEventReminders() {
        // Add logic to send reminders 30 minutes before the event's scheduled time
        // Retrieve upcoming events and notify subscribers
    }

    // Other message mapping methods for event updates and cancellations
}
