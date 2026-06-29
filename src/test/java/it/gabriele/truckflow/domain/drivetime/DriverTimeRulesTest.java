package it.gabriele.truckflow.domain.drivetime;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import org.junit.jupiter.api.Test;

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
