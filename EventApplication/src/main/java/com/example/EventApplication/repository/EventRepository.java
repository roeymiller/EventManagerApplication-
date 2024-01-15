package com.example.EventApplication.repository;

import com.example.EventApplication.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.location LIKE %:pattern% OR e.venue LIKE %:pattern%")
    List<Event> findByLocationOrVenueLike(@Param("pattern") String pattern);
}
