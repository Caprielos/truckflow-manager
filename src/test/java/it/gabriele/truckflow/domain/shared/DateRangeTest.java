package it.gabriele.truckflow.domain.shared;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa il Value Object DateRange.
 */
class DateRangeTest {

    @Test
    void shouldCreateDateRangeUsingLocalDate() {
        DateRange range = DateRange.of(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );

        assertEquals(LocalDate.of(2026, 1, 1), range.getStartDate());
        assertEquals(LocalDate.of(2026, 12, 31), range.getEndDate());
    }

    @Test
    void shouldCreateDateRangeUsingStrings() {
        DateRange range = DateRange.of("2026-01-01", "2026-12-31");

        assertEquals(LocalDate.of(2026, 1, 1), range.getStartDate());
        assertEquals(LocalDate.of(2026, 12, 31), range.getEndDate());
    }

    @Test
    void shouldAllowSameStartAndEndDate() {
        DateRange range = DateRange.of("2026-01-01", "2026-01-01");

        assertEquals(1, range.daysInclusive());
    }

    @Test
    void shouldNotAllowNullStartOrEndDate() {
        assertThrows(IllegalArgumentException.class,
                () -> DateRange.of(null, LocalDate.of(2026, 12, 31)));

        assertThrows(IllegalArgumentException.class,
                () -> DateRange.of(LocalDate.of(2026, 1, 1), null));
    }

    @Test
    void shouldNotAllowStartDateAfterEndDate() {
        assertThrows(IllegalArgumentException.class,
                () -> DateRange.of("2026-12-31", "2026-01-01"));
    }

    @Test
    void shouldCheckIfDateIsInsideRange() {
        DateRange range = DateRange.of("2026-01-01", "2026-12-31");

        assertTrue(range.contains(LocalDate.of(2026, 1, 1)));
        assertTrue(range.contains(LocalDate.of(2026, 6, 15)));
        assertTrue(range.contains(LocalDate.of(2026, 12, 31)));
        assertFalse(range.contains(LocalDate.of(2027, 1, 1)));
    }

    @Test
    void shouldNotCheckNullDate() {
        DateRange range = DateRange.of("2026-01-01", "2026-12-31");

        assertThrows(IllegalArgumentException.class, () -> range.contains(null));
    }

    @Test
    void shouldDetectOverlappingRanges() {
        DateRange first = DateRange.of("2026-01-01", "2026-06-30");
        DateRange second = DateRange.of("2026-06-01", "2026-12-31");

        assertTrue(first.overlapsWith(second));
    }

    @Test
    void shouldDetectNonOverlappingRanges() {
        DateRange first = DateRange.of("2026-01-01", "2026-06-30");
        DateRange second = DateRange.of("2026-07-01", "2026-12-31");

        assertFalse(first.overlapsWith(second));
    }

    @Test
    void shouldNotCheckOverlapWithNullRange() {
        DateRange range = DateRange.of("2026-01-01", "2026-12-31");

        assertThrows(IllegalArgumentException.class, () -> range.overlapsWith(null));
    }

    @Test
    void shouldCheckIfRangeIsInsideAnotherRange() {
        DateRange contractPeriod = DateRange.of("2026-03-01", "2026-06-30");
        DateRange fullYear = DateRange.of("2026-01-01", "2026-12-31");

        assertTrue(contractPeriod.isInside(fullYear));
    }

    @Test
    void shouldDetectWhenRangeIsNotInsideAnotherRange() {
        DateRange contractPeriod = DateRange.of("2025-12-01", "2026-06-30");
        DateRange fullYear = DateRange.of("2026-01-01", "2026-12-31");

        assertFalse(contractPeriod.isInside(fullYear));
    }

    @Test
    void shouldNotCheckInsideWithNullRange() {
        DateRange range = DateRange.of("2026-01-01", "2026-12-31");

        assertThrows(IllegalArgumentException.class, () -> range.isInside(null));
    }

    @Test
    void shouldCalculateInclusiveDays() {
        DateRange range = DateRange.of("2026-01-01", "2026-01-10");

        assertEquals(10, range.daysInclusive());
    }

    @Test
    void shouldConsiderEquivalentDateRangesEqual() {
        DateRange first = DateRange.of("2026-01-01", "2026-12-31");
        DateRange second = DateRange.of(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31)
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
