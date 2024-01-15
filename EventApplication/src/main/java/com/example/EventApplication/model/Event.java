package com.example.EventApplication.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Events")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Event {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "location", nullable = true)
    private String location;

    @Column(name = "venue", nullable = true)
    private String venue;

    @Column(name = "title", nullable = true)
    private String title;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "duration", nullable = true)
    private Integer durationInMinutes;

    @Column(name = "notificationTime")
    private final Integer notificationTimeInMinutes=30;

    @JoinColumn(name = "organizer_id",nullable = false)
    private Long organizerId;

    @JoinColumn(name = "event_id")
    private Set<Long> subscriberIds;

    @Column(name = "numberOfParticipants")
    private int numberOfParticipants;
}
