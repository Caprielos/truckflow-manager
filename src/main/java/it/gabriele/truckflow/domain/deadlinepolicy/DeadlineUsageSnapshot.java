package it.gabriele.truckflow.domain.deadlinepolicy;

import java.time.LocalDate;

/** Misure reali usate per calcolare scadenze a data, chilometri e ore. */
public record DeadlineUsageSnapshot(
    LocalDate today,
    LocalDate lastPerformedDate,
    Long lastOdometerKilometers,
    Long currentOdometerKilometers,
    Long lastEngineHours,
    Long currentEngineHours,
    Long lastRefrigerationHours,
    Long currentRefrigerationHours) {

  public DeadlineUsageSnapshot {
    if (today == null) {
      throw new IllegalArgumentException("La data di calcolo è obbligatoria.");
    }
    if (lastPerformedDate != null && lastPerformedDate.isAfter(today)) {
      throw new IllegalArgumentException("La data ultimo intervento non può essere futura.");
    }
    validateProgressiveCounter(lastOdometerKilometers, currentOdometerKilometers, "chilometri");
    validateProgressiveCounter(lastEngineHours, currentEngineHours, "ore motore");
    validateProgressiveCounter(lastRefrigerationHours, currentRefrigerationHours, "ore frigo");
  }

  public static DeadlineUsageSnapshot calendarOnly(LocalDate today, LocalDate lastPerformedDate) {
    return new DeadlineUsageSnapshot(today, lastPerformedDate, null, null, null, null, null, null);
  }

  public static DeadlineUsageSnapshot vehicleUsage(
      LocalDate today,
      LocalDate lastPerformedDate,
      long lastOdometerKilometers,
      long currentOdometerKilometers,
      long lastEngineHours,
      long currentEngineHours) {
    return new DeadlineUsageSnapshot(
        today,
        lastPerformedDate,
        lastOdometerKilometers,
        currentOdometerKilometers,
        lastEngineHours,
        currentEngineHours,
        null,
        null);
  }

  public DeadlineUsageSnapshot withRefrigerationHours(long lastHours, long currentHours) {
    return new DeadlineUsageSnapshot(
        today,
        lastPerformedDate,
        lastOdometerKilometers,
        currentOdometerKilometers,
        lastEngineHours,
        currentEngineHours,
        lastHours,
        currentHours);
  }

  public long kilometersSinceLastEvent() {
    if (lastOdometerKilometers == null || currentOdometerKilometers == null) {
      return 0;
    }
    return currentOdometerKilometers - lastOdometerKilometers;
  }

  public long engineHoursSinceLastEvent() {
    if (lastEngineHours == null || currentEngineHours == null) {
      return 0;
    }
    return currentEngineHours - lastEngineHours;
  }

  public long refrigerationHoursSinceLastEvent() {
    if (lastRefrigerationHours == null || currentRefrigerationHours == null) {
      return 0;
    }
    return currentRefrigerationHours - lastRefrigerationHours;
  }

  private static void validateProgressiveCounter(Long previous, Long current, String label) {
    if (previous != null && previous < 0) {
      throw new IllegalArgumentException(
          "Il contatore precedente " + label + " non può essere negativo.");
    }
    if (current != null && current < 0) {
      throw new IllegalArgumentException(
          "Il contatore corrente " + label + " non può essere negativo.");
    }
    if (previous != null && current != null && current < previous) {
      throw new IllegalArgumentException(
          "Il contatore corrente " + label + " non può essere minore del precedente.");
    }
  }
}
