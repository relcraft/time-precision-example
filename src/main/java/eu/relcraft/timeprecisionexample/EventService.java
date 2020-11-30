package eu.relcraft.timeprecisionexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class EventService {
    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public long findEventCountIfStartIsLessOrEqualBadVersion(LocalDate day) {
        LocalDateTime dayEnd = LocalDateTime.of(day, LocalTime.MAX);
        return eventRepository.findEventCountIfStartIsLessOrEqual(dayEnd);
    }

    public long findEventCountIfStartIsLessOrEqual(LocalDate day) {
        LocalDate nextDay = day.plusDays(1);
        LocalDateTime nextDayStart = LocalDateTime.of(nextDay, LocalTime.MIN);

        return eventRepository.findEventCountIfStartIsLess(nextDayStart);
    }
}
