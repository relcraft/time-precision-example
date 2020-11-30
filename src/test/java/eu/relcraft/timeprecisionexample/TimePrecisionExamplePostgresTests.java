package eu.relcraft.timeprecisionexample;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;


class TimePrecisionExamplePostgresTests extends TimePrecisionExampleTests {

    @Test
    void shouldReturnOneButReturnsTwo() {
        long count = eventService.findEventCountIfStartIsLessOrEqualBadVersion(testedDay);
        assertThat(count, is(2L));
    }

    @Test
    void returnOne() {
        long count = eventService.findEventCountIfStartIsLessOrEqual(testedDay);
        assertThat(count, is(1L));
    }
}
