package eu.relcraft.timeprecisionexample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("Select count(*) from Event event " +
            "where event.start <= :dateTime")
    long findEventCountIfStartIsLessOrEqual(@Param("dateTime") LocalDateTime dateTime);

    @Query("Select count(*) from Event event " +
            "where event.start < :dateTime")
    long findEventCountIfStartIsLess(@Param("dateTime") LocalDateTime dateTime);
}
