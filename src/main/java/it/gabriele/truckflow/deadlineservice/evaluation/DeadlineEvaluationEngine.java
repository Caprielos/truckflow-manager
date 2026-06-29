package it.gabriele.truckflow.deadlineservice.evaluation;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRuleIntervalType;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePack;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePackRule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/** Motore puro e stateless che valuta un oggetto generico rispetto a un rule pack. */
public final class DeadlineEvaluationEngine {

  public DeadlineEvaluationPlan evaluate(
      DeadlineSubject subject, DeadlineRulePack rulePack, LocalDate evaluationDate) {
    if (subject == null) {
      throw new IllegalArgumentException("subject è obbligatorio.");
    }
    if (rulePack == null) {
      throw new IllegalArgumentException("rulePack è obbligatorio.");
    }
    if (evaluationDate == null) {
      throw new IllegalArgumentException("evaluationDate è obbligatoria.");
    }

    List<DeadlineEvaluation> evaluations = new ArrayList<>();
    for (ManagedElementCode elementCode : subject.elements()) {
      evaluations.add(evaluateElement(subject, rulePack, elementCode, evaluationDate));
    }
    return DeadlineEvaluationPlan.from(subject, evaluationDate, evaluations);
  }

  private DeadlineEvaluation evaluateElement(
      DeadlineSubject subject,
      DeadlineRulePack rulePack,
      ManagedElementCode elementCode,
      LocalDate evaluationDate) {
    List<DeadlineRulePackRule> rules = rulePack.rulesForElement(elementCode);
    if (rules.isEmpty()) {
      return missingConfiguration(
          subject,
          elementCode,
          "Nessuna regola o slot configurativo presente nel rule pack per questo elemento.");
    }

    List<DeadlineRulePackRule> activeRules =
        rules.stream().filter(DeadlineRulePackRule::isActive).toList();
    if (activeRules.isEmpty()) {
      Optional<DeadlineRulePackRule> emptySlot =
          rules.stream().filter(DeadlineRulePackRule::isEmptySlot).findFirst();
      if (emptySlot.isPresent()) {
        DeadlineRulePackRule slot = emptySlot.get();
        return new DeadlineEvaluation(
            subject.objectRef(),
            elementCode,
            DeadlineEvaluationStatus.CONFIGURATION_MISSING,
            slot.ruleId(),
            slot.sourceTypes(),
            null,
            null,
            false,
            "Esiste lo slot configurabile, ma la regola non è ancora stata compilata.");
      }
      return new DeadlineEvaluation(
          subject.objectRef(),
          elementCode,
          DeadlineEvaluationStatus.SUSPENDED,
          rules.getFirst().ruleId(),
          rules.getFirst().sourceTypes(),
          null,
          null,
          false,
          "Tutte le regole disponibili per questo elemento sono sospese.");
    }

    return activeRules.stream()
        .map(rule -> evaluateActiveRule(subject, elementCode, rule, evaluationDate))
        .max(Comparator.comparingInt(evaluation -> severity(evaluation.status())))
        .orElseGet(
            () ->
                missingConfiguration(
                    subject, elementCode, "Nessuna regola attiva valutabile per questo elemento."));
  }

  private DeadlineEvaluation evaluateActiveRule(
      DeadlineSubject subject,
      ManagedElementCode elementCode,
      DeadlineRulePackRule rule,
      LocalDate evaluationDate) {
    if (rule.intervalType() == DeadlineRuleIntervalType.NOT_CONFIGURED) {
      return new DeadlineEvaluation(
          subject.objectRef(),
          elementCode,
          DeadlineEvaluationStatus.CONFIGURATION_MISSING,
          rule.ruleId(),
          rule.sourceTypes(),
          null,
          null,
          rule.blocksOperation(),
          "La regola è attiva, ma non ha ancora un intervallo di calcolo configurato.");
    }

    if (rule.intervalType() == DeadlineRuleIntervalType.CONTINUOUS_EVENT) {
      return evaluateContinuousEvent(subject, elementCode, rule);
    }

    LocalDate nextDueDate = resolveNextDueDate(subject, elementCode, rule).orElse(null);
    Long nextDueKm = resolveNextDueKm(subject, elementCode, rule).orElse(null);

    boolean needsDate =
        rule.intervalType() == DeadlineRuleIntervalType.DATE_BASED
            || rule.intervalType() == DeadlineRuleIntervalType.DATE_OR_DISTANCE;
    boolean needsKm =
        rule.intervalType() == DeadlineRuleIntervalType.DISTANCE_BASED
            || rule.intervalType() == DeadlineRuleIntervalType.DATE_OR_DISTANCE;

    if ((needsDate && nextDueDate == null) || (needsKm && nextDueKm == null)) {
      return new DeadlineEvaluation(
          subject.objectRef(),
          elementCode,
          DeadlineEvaluationStatus.MANUAL_REVIEW_REQUIRED,
          rule.ruleId(),
          rule.sourceTypes(),
          nextDueDate,
          nextDueKm,
          rule.blocksOperation(),
          "Mancano fatti sufficienti per calcolare automaticamente la prossima scadenza.");
    }

    DeadlineEvaluationStatus status = DeadlineEvaluationStatus.OK;
    if (nextDueDate != null) {
      status =
          moreSevere(
              status, evaluateDateStatus(nextDueDate, evaluationDate, rule.warningDaysBefore()));
    }
    if (nextDueKm != null) {
      Optional<Long> currentKm = factLong(subject, "currentKm");
      if (currentKm.isEmpty()) {
        status = moreSevere(status, DeadlineEvaluationStatus.MANUAL_REVIEW_REQUIRED);
      } else {
        status =
            moreSevere(
                status, evaluateKmStatus(nextDueKm, currentKm.get(), rule.warningKmBefore()));
      }
    }

    return new DeadlineEvaluation(
        subject.objectRef(),
        elementCode,
        status,
        rule.ruleId(),
        rule.sourceTypes(),
        nextDueDate,
        nextDueKm,
        rule.blocksOperation(),
        buildExplanation(status, nextDueDate, nextDueKm));
  }

  private DeadlineEvaluation evaluateContinuousEvent(
      DeadlineSubject subject, ManagedElementCode elementCode, DeadlineRulePackRule rule) {
    String eventStatus =
        fact(subject, elementFactKey("eventStatus", elementCode))
            .orElse("OK")
            .toUpperCase(Locale.ROOT);
    DeadlineEvaluationStatus status =
        switch (eventStatus) {
          case "ALERT", "DUE_SOON" -> DeadlineEvaluationStatus.DUE_SOON;
          case "DUE_NOW" -> DeadlineEvaluationStatus.DUE_NOW;
          case "OVERDUE" -> DeadlineEvaluationStatus.OVERDUE;
          case "BLOCKING" -> DeadlineEvaluationStatus.BLOCKING;
          case "MANUAL_REVIEW" -> DeadlineEvaluationStatus.MANUAL_REVIEW_REQUIRED;
          default -> DeadlineEvaluationStatus.OK;
        };
    return new DeadlineEvaluation(
        subject.objectRef(),
        elementCode,
        status,
        rule.ruleId(),
        rule.sourceTypes(),
        null,
        null,
        rule.blocksOperation(),
        "Regola di monitoraggio continuo valutata da evento o assenza di evento critico.");
  }

  private Optional<LocalDate> resolveNextDueDate(
      DeadlineSubject subject, ManagedElementCode elementCode, DeadlineRulePackRule rule) {
    Optional<LocalDate> explicitDueDate = factDate(subject, elementFactKey("dueDate", elementCode));
    if (explicitDueDate.isPresent()) {
      return explicitDueDate;
    }
    if (rule.intervalDays() == null) {
      return Optional.empty();
    }
    return factDate(subject, elementFactKey("lastDate", elementCode))
        .or(() -> factDate(subject, "lastDate"))
        .map(lastDate -> lastDate.plusDays(rule.intervalDays()));
  }

  private Optional<Long> resolveNextDueKm(
      DeadlineSubject subject, ManagedElementCode elementCode, DeadlineRulePackRule rule) {
    Optional<Long> explicitDueKm = factLong(subject, elementFactKey("dueKm", elementCode));
    if (explicitDueKm.isPresent()) {
      return explicitDueKm;
    }
    if (rule.intervalKm() == null) {
      return Optional.empty();
    }
    return factLong(subject, elementFactKey("lastKm", elementCode))
        .or(() -> factLong(subject, "lastKm"))
        .map(lastKm -> lastKm + rule.intervalKm());
  }

  private DeadlineEvaluationStatus evaluateDateStatus(
      LocalDate nextDueDate, LocalDate evaluationDate, int warningDaysBefore) {
    if (nextDueDate.isBefore(evaluationDate)) {
      return DeadlineEvaluationStatus.OVERDUE;
    }
    if (nextDueDate.isEqual(evaluationDate)) {
      return DeadlineEvaluationStatus.DUE_NOW;
    }
    long remainingDays = ChronoUnit.DAYS.between(evaluationDate, nextDueDate);
    return remainingDays <= warningDaysBefore
        ? DeadlineEvaluationStatus.DUE_SOON
        : DeadlineEvaluationStatus.OK;
  }

  private DeadlineEvaluationStatus evaluateKmStatus(
      long nextDueKm, long currentKm, long warningKmBefore) {
    if (currentKm > nextDueKm) {
      return DeadlineEvaluationStatus.OVERDUE;
    }
    if (currentKm == nextDueKm) {
      return DeadlineEvaluationStatus.DUE_NOW;
    }
    return nextDueKm - currentKm <= warningKmBefore
        ? DeadlineEvaluationStatus.DUE_SOON
        : DeadlineEvaluationStatus.OK;
  }

  private DeadlineEvaluation missingConfiguration(
      DeadlineSubject subject, ManagedElementCode elementCode, String explanation) {
    return new DeadlineEvaluation(
        subject.objectRef(),
        elementCode,
        DeadlineEvaluationStatus.CONFIGURATION_MISSING,
        "",
        java.util.Set.of(),
        null,
        null,
        false,
        explanation);
  }

  private static String buildExplanation(
      DeadlineEvaluationStatus status, LocalDate nextDueDate, Long nextDueKm) {
    return "Stato "
        + status
        + " calcolato da prossima data="
        + (nextDueDate == null ? "non configurata" : nextDueDate)
        + " e prossimo km="
        + (nextDueKm == null ? "non configurato" : nextDueKm)
        + ".";
  }

  private static DeadlineEvaluationStatus moreSevere(
      DeadlineEvaluationStatus first, DeadlineEvaluationStatus second) {
    return severity(second) > severity(first) ? second : first;
  }

  private static int severity(DeadlineEvaluationStatus status) {
    return switch (status) {
      case NOT_APPLICABLE -> 0;
      case OK -> 1;
      case CONFIGURATION_MISSING -> 2;
      case SUSPENDED -> 3;
      case DUE_SOON -> 4;
      case MANUAL_REVIEW_REQUIRED -> 5;
      case DUE_NOW -> 6;
      case OVERDUE -> 7;
      case BLOCKING -> 8;
    };
  }

  private static Optional<String> fact(DeadlineSubject subject, String key) {
    String value = subject.fact(key);
    return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.strip());
  }

  private static Optional<LocalDate> factDate(DeadlineSubject subject, String key) {
    return fact(subject, key).map(LocalDate::parse);
  }

  private static Optional<Long> factLong(DeadlineSubject subject, String key) {
    return fact(subject, key).map(Long::parseLong);
  }

  private static String elementFactKey(String prefix, ManagedElementCode elementCode) {
    return prefix + "." + elementCode.name();
  }
}
