package it.gabriele.truckflow.domain.deadlinepolicy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/** Regole di dominio per combinare scadenze legali e tecniche. */
public final class DeadlinePolicyRules {

  private DeadlinePolicyRules() {}

  public static CalculatedDeadline calculate(
      DeadlinePolicyRule rule, String ownerCode, DeadlineUsageSnapshot snapshot) {
    Objects.requireNonNull(rule, "La regola scadenza è obbligatoria.");
    Objects.requireNonNull(snapshot, "Le misure scadenza sono obbligatorie.");

    DeadlineRuleInterval interval = rule.getInterval();
    LocalDate dueDate = calculateDueDate(interval, snapshot);
    LocalDate warningDate =
        dueDate == null ? null : dueDate.minusDays(interval.effectiveWarningDays());
    Long dueOdometer = calculateDueOdometer(interval, snapshot);
    Long dueEngineHours = calculateDueEngineHours(interval, snapshot);
    Long dueRefrigerationHours = calculateDueRefrigerationHours(interval, snapshot);
    boolean alreadyDue =
        isAlreadyDue(
            interval, dueDate, dueOdometer, dueEngineHours, dueRefrigerationHours, snapshot);
    boolean dueSoon = !alreadyDue && isDueSoon(interval, dueDate, snapshot);

    return new CalculatedDeadline(
        rule,
        ownerCode,
        dueDate,
        warningDate,
        dueOdometer,
        dueEngineHours,
        dueRefrigerationHours,
        alreadyDue,
        dueSoon);
  }

  public static CombinedDeadlinePlan combine(
      String ownerCode,
      ManagedDeadlineElementType elementType,
      List<DeadlinePolicyRule> legalRules,
      List<DeadlinePolicyRule> technicalRules,
      DeadlineUsageSnapshot snapshot) {
    Objects.requireNonNull(elementType, "Il tipo elemento è obbligatorio.");
    Objects.requireNonNull(snapshot, "Le misure scadenza sono obbligatorie.");

    List<CalculatedDeadline> legalDeadlines =
        sanitizeRules(legalRules).stream()
            .filter(rule -> rule.getElementType() == elementType)
            .map(rule -> calculate(rule, ownerCode, snapshot))
            .toList();

    List<CalculatedDeadline> technicalDeadlines =
        sanitizeRules(technicalRules).stream()
            .filter(rule -> rule.getElementType() == elementType)
            .map(rule -> calculate(rule, ownerCode, snapshot))
            .toList();

    return new CombinedDeadlinePlan(ownerCode, elementType, legalDeadlines, technicalDeadlines);
  }

  private static List<DeadlinePolicyRule> sanitizeRules(List<DeadlinePolicyRule> rules) {
    if (rules == null) {
      return List.of();
    }
    if (rules.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Le regole scadenza non possono contenere null.");
    }
    return List.copyOf(rules);
  }

  private static LocalDate calculateDueDate(
      DeadlineRuleInterval interval, DeadlineUsageSnapshot snapshot) {
    if (!interval.hasCalendarInterval() || snapshot.lastPerformedDate() == null) {
      return null;
    }
    return snapshot.lastPerformedDate().plusMonths(interval.calendarMonths());
  }

  private static Long calculateDueOdometer(
      DeadlineRuleInterval interval, DeadlineUsageSnapshot snapshot) {
    if (interval.odometerKilometers() == null || snapshot.lastOdometerKilometers() == null) {
      return null;
    }
    return snapshot.lastOdometerKilometers() + interval.odometerKilometers();
  }

  private static Long calculateDueEngineHours(
      DeadlineRuleInterval interval, DeadlineUsageSnapshot snapshot) {
    if (interval.engineHours() == null || snapshot.lastEngineHours() == null) {
      return null;
    }
    return snapshot.lastEngineHours() + interval.engineHours();
  }

  private static Long calculateDueRefrigerationHours(
      DeadlineRuleInterval interval, DeadlineUsageSnapshot snapshot) {
    if (interval.refrigerationHours() == null || snapshot.lastRefrigerationHours() == null) {
      return null;
    }
    return snapshot.lastRefrigerationHours() + interval.refrigerationHours();
  }

  private static boolean isAlreadyDue(
      DeadlineRuleInterval interval,
      LocalDate dueDate,
      Long dueOdometer,
      Long dueEngineHours,
      Long dueRefrigerationHours,
      DeadlineUsageSnapshot snapshot) {
    if (dueDate != null && snapshot.today().isAfter(dueDate)) {
      return true;
    }
    if (dueOdometer != null
        && snapshot.currentOdometerKilometers() != null
        && snapshot.currentOdometerKilometers() >= dueOdometer) {
      return true;
    }
    if (dueEngineHours != null
        && snapshot.currentEngineHours() != null
        && snapshot.currentEngineHours() >= dueEngineHours) {
      return true;
    }
    return dueRefrigerationHours != null
        && snapshot.currentRefrigerationHours() != null
        && snapshot.currentRefrigerationHours() >= dueRefrigerationHours;
  }

  private static boolean isDueSoon(
      DeadlineRuleInterval interval, LocalDate dueDate, DeadlineUsageSnapshot snapshot) {
    return dueDate != null
        && !snapshot.today().isBefore(dueDate.minusDays(interval.effectiveWarningDays()))
        && !snapshot.today().isAfter(dueDate);
  }
}
