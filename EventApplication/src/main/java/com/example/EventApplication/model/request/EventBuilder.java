package com.example.EventApplication.model.request;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;


@Builder
@Data
public class EventBuilder {
    private Long id;

    private LocalDateTime dateTime;

    private LocalDateTime creationDate;

    private String location;

    private String venue;

    private String title;

    private String description;

    private Integer durationInMinutes;

    private final Integer notificationTimeInMinutes=30;

    private Long organizerId;

    private Set<Long> subscriberIds;
}
