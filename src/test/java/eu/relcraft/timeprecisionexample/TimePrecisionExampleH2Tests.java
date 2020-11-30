package eu.relcraft.timeprecisionexample;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@ActiveProfiles("h2")
class TimePrecisionExampleH2Tests extends TimePrecisionExampleTests {

    @Test
    void returnsOneEvenUsingBadVersion() {
        long count = eventService.findEventCountIfStartIsLessOrEqualBadVersion(testedDay);
        assertThat(count, is(1L));
    }

    @Test
    void returnsOne() {
        long count = eventService.findEventCountIfStartIsLessOrEqual(testedDay);
        assertThat(count, is(1L));
    }
}
