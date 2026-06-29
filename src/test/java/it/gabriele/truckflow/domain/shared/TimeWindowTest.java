package it.gabriele.truckflow.domain.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

/** Testa il Value Object TimeWindow. */
class TimeWindowTest {

  @Test
  void shouldCreateTimeWindowUsingLocalTime() {
    TimeWindow timeWindow = TimeWindow.of(LocalTime.of(8, 0), LocalTime.of(12, 0));

    assertEquals(LocalTime.of(8, 0), timeWindow.getStartTime());
    assertEquals(LocalTime.of(12, 0), timeWindow.getEndTime());
  }

  @Test
  void shouldCreateTimeWindowUsingStrings() {
    TimeWindow timeWindow = TimeWindow.of("08:00", "12:00");

    assertEquals(LocalTime.of(8, 0), timeWindow.getStartTime());
    assertEquals(LocalTime.of(12, 0), timeWindow.getEndTime());
  }

  @Test
  void shouldNotAllowNullStartOrEndTime() {
    assertThrows(IllegalArgumentException.class, () -> TimeWindow.of(null, LocalTime.of(12, 0)));
    assertThrows(IllegalArgumentException.class, () -> TimeWindow.of(LocalTime.of(8, 0), null));
  }

  @Test
  void shouldNotAllowStartTimeEqualToEndTime() {
    assertThrows(
        IllegalArgumentException.class,
        () -> TimeWindow.of(LocalTime.of(8, 0), LocalTime.of(8, 0)));
  }

  @Test
  void shouldNotAllowStartTimeAfterEndTime() {
    assertThrows(
        IllegalArgumentException.class,
        () -> TimeWindow.of(LocalTime.of(12, 0), LocalTime.of(8, 0)));
  }

  @Test
  void shouldCheckIfTimeIsInsideWindow() {
    TimeWindow timeWindow = TimeWindow.of("08:00", "12:00");

    assertTrue(timeWindow.contains(LocalTime.of(8, 0)));
    assertTrue(timeWindow.contains(LocalTime.of(10, 0)));
    assertTrue(timeWindow.contains(LocalTime.of(12, 0)));
    assertFalse(timeWindow.contains(LocalTime.of(13, 0)));
  }

  @Test
  void shouldNotCheckNullTime() {
    TimeWindow timeWindow = TimeWindow.of("08:00", "12:00");

    assertThrows(IllegalArgumentException.class, () -> timeWindow.contains(null));
  }

  @Test
  void shouldDetectOverlappingWindows() {
    TimeWindow first = TimeWindow.of("08:00", "12:00");
    TimeWindow second = TimeWindow.of("10:00", "14:00");

    assertTrue(first.overlapsWith(second));
  }

  @Test
  void shouldDetectNonOverlappingWindows() {
    TimeWindow first = TimeWindow.of("08:00", "12:00");
    TimeWindow second = TimeWindow.of("13:00", "15:00");

    assertFalse(first.overlapsWith(second));
  }

  @Test
  void shouldNotCheckOverlapWithNullWindow() {
    TimeWindow timeWindow = TimeWindow.of("08:00", "12:00");

    assertThrows(IllegalArgumentException.class, () -> timeWindow.overlapsWith(null));
  }

  @Test
  void shouldCheckIfWindowIsInsideAnotherWindow() {
    TimeWindow appointmentWindow = TimeWindow.of("09:00", "11:00");
    TimeWindow warehouseWindow = TimeWindow.of("08:00", "12:00");

    assertTrue(appointmentWindow.isInside(warehouseWindow));
  }

  @Test
  void shouldDetectWhenWindowIsNotInsideAnotherWindow() {
    TimeWindow appointmentWindow = TimeWindow.of("07:00", "11:00");
    TimeWindow warehouseWindow = TimeWindow.of("08:00", "12:00");

    assertFalse(appointmentWindow.isInside(warehouseWindow));
  }

  @Test
  void shouldNotCheckInsideWithNullWindow() {
    TimeWindow timeWindow = TimeWindow.of("08:00", "12:00");

    assertThrows(IllegalArgumentException.class, () -> timeWindow.isInside(null));
  }

  @Test
  void shouldConsiderEquivalentTimeWindowsEqual() {
    TimeWindow first = TimeWindow.of("08:00", "12:00");
    TimeWindow second = TimeWindow.of(LocalTime.of(8, 0), LocalTime.of(12, 0));

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }
}
