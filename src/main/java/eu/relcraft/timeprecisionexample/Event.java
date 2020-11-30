package eu.relcraft.timeprecisionexample;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private LocalDateTime start;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", start=" + start +
                '}';
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStart() {
        return start;
    }
}
