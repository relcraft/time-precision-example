package eu.relcraft.timeprecisionexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
public class TimePrecisionExampleTests {
    final LocalDate testedDay = LocalDate.of(2020, Month.NOVEMBER, 12);

    @Autowired
    EventService eventService;
}
