package it.gabriele.truckflow.domain.deadlinepolicy;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Piano scadenze calcolato combinando regole legali e tecniche. */
public record CombinedDeadlinePlan(
    String ownerCode,
    ManagedDeadlineElementType elementType,
    List<CalculatedDeadline> legalDeadlines,
    List<CalculatedDeadline> technicalDeadlines) {

  public CombinedDeadlinePlan {
    if (ownerCode == null || ownerCode.trim().isEmpty()) {
      throw new IllegalArgumentException("Il codice proprietario piano scadenze è obbligatorio.");
    }
    ownerCode = ownerCode.trim().toUpperCase();
    if (elementType == null) {
      throw new IllegalArgumentException("Il tipo elemento piano scadenze è obbligatorio.");
    }
    legalDeadlines = legalDeadlines == null ? List.of() : List.copyOf(legalDeadlines);
    technicalDeadlines = technicalDeadlines == null ? List.of() : List.copyOf(technicalDeadlines);
  }

  public List<CalculatedDeadline> allDeadlines() {
    return java.util.stream.Stream.concat(legalDeadlines.stream(), technicalDeadlines.stream())
        .toList();
  }

  public Optional<CalculatedDeadline> nextEffectiveDeadline() {
    return allDeadlines().stream().min(CombinedDeadlinePlan::compareDeadlines);
  }

  public boolean hasBlockingDeadline() {
    return allDeadlines().stream().anyMatch(CalculatedDeadline::blocksOperationsNow);
  }

  public boolean hasLegalRules() {
    return !legalDeadlines.isEmpty();
  }

  public boolean hasTechnicalRules() {
    return !technicalDeadlines.isEmpty();
  }

  private static int compareDeadlines(CalculatedDeadline left, CalculatedDeadline right) {
    if (left.alreadyDue() != right.alreadyDue()) {
      return left.alreadyDue() ? -1 : 1;
    }

    LocalDate leftDate = left.dueDate();
    LocalDate rightDate = right.dueDate();

    if (leftDate != null && rightDate != null) {
      return leftDate.compareTo(rightDate);
    }
    if (leftDate != null) {
      return -1;
    }
    if (rightDate != null) {
      return 1;
    }

    return Comparator.comparing((CalculatedDeadline deadline) -> deadline.rule().getRuleCode())
        .compare(left, right);
  }
}
