package it.gabriele.truckflow.domain.drivetime;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DriverTimeRulesTest {

    @Test
    void shouldApplyDrivingAndRestLimits() {
        assertFalse(DriverTimeRules.requiresBreakAfter(Duration.ofHours(4)));
        assertTrue(DriverTimeRules.requiresBreakAfter(Duration.ofHours(4).plusMinutes(30)));
        assertTrue(DriverTimeRules.isWithinStandardDailyDriving(Duration.ofHours(9)));
        assertFalse(DriverTimeRules.isWithinStandardDailyDriving(Duration.ofHours(10)));
        assertEquals(Duration.ofMinutes(45), DriverTimeRules.requiredBreak());
        assertEquals(Duration.ofHours(11), DriverTimeRules.standardDailyRest());
    }
}
