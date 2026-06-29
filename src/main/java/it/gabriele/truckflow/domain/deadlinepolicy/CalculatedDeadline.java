package it.gabriele.truckflow.domain.deadlinepolicy;

import java.time.LocalDate;
import java.util.Objects;

/** Risultato del calcolo di una singola regola di scadenza. */
public record CalculatedDeadline(
    DeadlinePolicyRule rule,
    String ownerCode,
    LocalDate dueDate,
    LocalDate warningDate,
    Long dueOdometerKilometers,
    Long dueEngineHours,
    Long dueRefrigerationHours,
    boolean alreadyDue,
    boolean dueSoon) {

  public CalculatedDeadline {
    Objects.requireNonNull(rule, "La regola scadenza calcolata è obbligatoria.");
    ownerCode = normalizeOwnerCode(ownerCode);
    if (dueDate != null && warningDate != null && warningDate.isAfter(dueDate)) {
      throw new IllegalArgumentException("La data avviso non può superare la data scadenza.");
    }
  }

  public DeadlinePolicySource source() {
    return rule.getSource();
  }

  public ManagedDeadlineElementType elementType() {
    return rule.getElementType();
  }

  public boolean hasCalendarDueDate() {
    return dueDate != null;
  }

  public boolean blocksOperationsNow() {
    return rule.blocksOperations() && alreadyDue;
  }

  private static String normalizeOwnerCode(String ownerCode) {
    if (ownerCode == null || ownerCode.trim().isEmpty()) {
      throw new IllegalArgumentException(
          "Il codice proprietario scadenza calcolata è obbligatorio.");
    }
    return ownerCode.trim().toUpperCase();
  }
}
