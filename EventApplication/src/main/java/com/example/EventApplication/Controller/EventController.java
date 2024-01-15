package com.example.EventApplication.Controller;

import com.example.EventApplication.Exception.ControllerAdvice;
import com.example.EventApplication.Exception.NotFoundException;
import com.example.EventApplication.Service.EventService;
import com.example.EventApplication.Service.ReminderService;
import com.example.EventApplication.model.Event;
import com.example.EventApplication.model.request.EventBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Events", description = "Event management APIs")
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private ReminderService reminderService;

    @Operation(
            summary = "Send a reminder to All subscribers that has an event in the next 30 minutes)",
            description = "Send a reminder to All subscribers that has an event in the next 30 minutes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping(value = "/remind-subscribers")
    public ResponseEntity.BodyBuilder remindSubscribers() {

        reminderService.sendReminders();
        return ResponseEntity.ok();
    }

    @Operation(
            summary = "Retrieve all events by searchText and sortType(DATE,POPULARITY,CREATION_TIME)",
            description = "Get a List of event Objects.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping(value = "/get-all")
    public ResponseEntity<List<Event>> getAllEvents(@RequestParam(name = "pattern", required = false) String pattern, @RequestParam(name = "sortType", required = false) String sortType) {
        return ResponseEntity.ok(eventService.getAllEvents(pattern, sortType));
    }

    @Operation(
            summary = "Retrieve an event by Id",
            description = "Retrieve an event by Id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/get-by-id")
    public ResponseEntity<Optional<Event>> getEventById(@RequestParam(name = "id") long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @Operation(
            summary = "Add a single event",
            description = "Gets EventBuilder returns Event if creation succeeded")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/add")
    public ResponseEntity<? extends Object> addEvent(@RequestBody EventBuilder eventBuilder) {
        try {
            return ResponseEntity.ok(eventService.addEvent(eventBuilder));
        } catch (Exception ex) {
            return new ControllerAdvice().handleException(ex);
        }

    }

    @Operation(
            summary = "Add multiple events",
            description = "Gets List of EventBuilder returns List of Events if creation succeeded")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/add-events")
    public ResponseEntity<? extends Object> addEvents(@RequestBody List<EventBuilder> eventList) {
        try {
            return ResponseEntity.ok(eventService.addEvents(eventList));
        } catch (Exception ex) {
            return new ControllerAdvice().handleException(ex);
        }
    }

    @Operation(
            summary = "Update a single event",
            description = "Gets EventBuilder returns Event if update succeeded")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/update")
    public ResponseEntity<? extends Object> updateEvent(@RequestBody EventBuilder eventBuilder) {
        try {
            return ResponseEntity.ok(eventService.updateEvent(eventBuilder));
        } catch (NotFoundException ex) {
            return new ControllerAdvice().handleNotFoundException(ex);
        } catch (Exception ex) {
            return new ControllerAdvice().handleException(ex);
        }
    }

    @Operation(
            summary = "Update multiple events",
            description = "Gets a List of EventBuilders returns a List of Events if update succeeded")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/update-events")
    public ResponseEntity<? extends Object> updateEvents(@RequestBody List<EventBuilder> eventList) {
        try {
            return ResponseEntity.ok(eventService.updateEvents(eventList));
        } catch (NotFoundException ex) {
            return new ControllerAdvice().handleNotFoundException(ex);
        } catch (Exception ex) {
            return new ControllerAdvice().handleException(ex);
        }
    }

    @Operation(
            summary = "Delete a single event",
            description = "Gets Id returns id of deleted event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/delete")
    public ResponseEntity<Long> deleteEvent(@RequestParam(name = "id") long id) {
        return ResponseEntity.ok(eventService.deleteById(id));
    }

    @Operation(
            summary = "Delete multiple events",
            description = "Gets Ids returns ids of deleted events")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/delete-events")
    public ResponseEntity<List<Long>> deleteEvents(@RequestParam(name = "ids") List<Long> ids) {
        return ResponseEntity.ok(eventService.deleteByIds(ids));
    }

    @Operation(
            summary = "subscribe to one or multiple events",
            description = "Gets participantId and eventIds to subscribe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/subscribe")
    public ResponseEntity<List<Long>> subscribeEvents(@RequestParam(name = "participantId") Long participantId
            , @RequestParam(name = "eventIds") List<Long> eventIds) {
        return ResponseEntity.ok(eventService.subscribeEvents(participantId, eventIds));
    }

    @Operation(
            summary = "unsubscribe to one or multiple events",
            description = "Gets participantId and eventIds to unsubscribe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Event.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/unsubscribe")
    public ResponseEntity<List<Long>> unsubscribeEvents(@RequestParam(name = "participantId") Long participantId
            , @RequestParam(name = "eventIds") List<Long> eventIds) {
        return ResponseEntity.ok(eventService.unsubscribeEvents(participantId, eventIds));
    }
}
