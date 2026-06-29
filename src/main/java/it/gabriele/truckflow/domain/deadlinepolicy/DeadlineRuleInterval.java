package it.gabriele.truckflow.domain.deadlinepolicy;

/** Intervallo configurabile di una regola di scadenza. */
public record DeadlineRuleInterval(
    Integer calendarMonths,
    Long odometerKilometers,
    Long engineHours,
    Long refrigerationHours,
    Integer warningDays) {

  public DeadlineRuleInterval {
    if (calendarMonths == null
        && odometerKilometers == null
        && engineHours == null
        && refrigerationHours == null) {
      throw new IllegalArgumentException("Almeno un intervallo scadenza è obbligatorio.");
    }
    if (calendarMonths != null && calendarMonths <= 0) {
      throw new IllegalArgumentException("I mesi intervallo devono essere positivi.");
    }
    if (odometerKilometers != null && odometerKilometers <= 0) {
      throw new IllegalArgumentException("I chilometri intervallo devono essere positivi.");
    }
    if (engineHours != null && engineHours <= 0) {
      throw new IllegalArgumentException("Le ore motore intervallo devono essere positive.");
    }
    if (refrigerationHours != null && refrigerationHours <= 0) {
      throw new IllegalArgumentException("Le ore frigo intervallo devono essere positive.");
    }
    if (warningDays != null && warningDays < 0) {
      throw new IllegalArgumentException("I giorni di preavviso non possono essere negativi.");
    }
  }

  public static DeadlineRuleInterval calendarMonths(int months, int warningDays) {
    return new DeadlineRuleInterval(months, null, null, null, warningDays);
  }

  public static DeadlineRuleInterval kilometers(long kilometers, int warningDays) {
    return new DeadlineRuleInterval(null, kilometers, null, null, warningDays);
  }

  public static DeadlineRuleInterval monthsOrKilometers(
      int months, long kilometers, int warningDays) {
    return new DeadlineRuleInterval(months, kilometers, null, null, warningDays);
  }

  public static DeadlineRuleInterval monthsOrEngineHours(int months, long hours, int warningDays) {
    return new DeadlineRuleInterval(months, null, hours, null, warningDays);
  }

  public static DeadlineRuleInterval monthsOrRefrigerationHours(
      int months, long hours, int warningDays) {
    return new DeadlineRuleInterval(months, null, null, hours, warningDays);
  }

  public int effectiveWarningDays() {
    return warningDays == null ? 30 : warningDays;
  }

  public boolean hasCalendarInterval() {
    return calendarMonths != null;
  }

  public boolean hasUsageInterval() {
    return odometerKilometers != null || engineHours != null || refrigerationHours != null;
  }
}
