package it.gabriele.truckflow.domain.deadline;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

/** Scadenza enterprise collegata a una risorsa, un documento, un contratto o un processo. */
public final class EnterpriseDeadline {

  private static final int MAX_CODE_LENGTH = 50;

  private final String deadlineCode;
  private final DeadlineOwnerType ownerType;
  private final String ownerCode;
  private final DeadlineType type;
  private final LocalDate dueDate;
  private final LocalDate warningDate;
  private final DeadlineStatus status;
  private final DeadlineSeverity severity;
  private final Notes notes;

  private EnterpriseDeadline(
      String deadlineCode,
      DeadlineOwnerType ownerType,
      String ownerCode,
      DeadlineType type,
      LocalDate dueDate,
      LocalDate warningDate,
      DeadlineStatus status,
      DeadlineSeverity severity,
      Notes notes) {
    this.deadlineCode = validateCode(deadlineCode, "Il codice scadenza è obbligatorio.");

    if (ownerType == null) {
      throw new IllegalArgumentException("Il tipo proprietario scadenza è obbligatorio.");
    }

    this.ownerCode = validateCode(ownerCode, "Il codice proprietario scadenza è obbligatorio.");

    if (type == null) {
      throw new IllegalArgumentException("Il tipo scadenza è obbligatorio.");
    }

    if (dueDate == null) {
      throw new IllegalArgumentException("La data scadenza è obbligatoria.");
    }

    if (warningDate != null && warningDate.isAfter(dueDate)) {
      throw new IllegalArgumentException("La data avviso non può essere successiva alla scadenza.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato scadenza è obbligatorio.");
    }

    if (severity == null) {
      throw new IllegalArgumentException("La gravità scadenza è obbligatoria.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note scadenza sono obbligatorie.");
    }

    this.ownerType = ownerType;
    this.type = type;
    this.dueDate = dueDate;
    this.warningDate = warningDate;
    this.status = status;
    this.severity = severity;
    this.notes = notes;
  }

  public static EnterpriseDeadline planned(
      String deadlineCode,
      DeadlineOwnerType ownerType,
      String ownerCode,
      DeadlineType type,
      LocalDate dueDate,
      LocalDate warningDate,
      DeadlineSeverity severity,
      Notes notes) {
    return new EnterpriseDeadline(
        deadlineCode,
        ownerType,
        ownerCode,
        type,
        dueDate,
        warningDate,
        DeadlineStatus.PLANNED,
        severity,
        notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public EnterpriseDeadline refreshStatus(LocalDate today) {
    DeadlineStatus refreshedStatus = DeadlineRules.calculateStatus(this, today);
    return new EnterpriseDeadline(
        deadlineCode,
        ownerType,
        ownerCode,
        type,
        dueDate,
        warningDate,
        refreshedStatus,
        severity,
        notes);
  }

  public EnterpriseDeadline complete() {
    if (!DeadlineRules.canBeCompleted(this)) {
      throw new IllegalStateException("La scadenza non può essere completata.");
    }

    return new EnterpriseDeadline(
        deadlineCode,
        ownerType,
        ownerCode,
        type,
        dueDate,
        warningDate,
        DeadlineStatus.COMPLETED,
        severity,
        notes);
  }

  public EnterpriseDeadline waive(Notes waiverNotes) {
    if (!DeadlineRules.canBeWaived(this)) {
      throw new IllegalStateException("La scadenza non può essere derogata.");
    }

    if (waiverNotes == null || waiverNotes.isEmpty()) {
      throw new IllegalArgumentException("Le note deroga sono obbligatorie.");
    }

    return new EnterpriseDeadline(
        deadlineCode,
        ownerType,
        ownerCode,
        type,
        dueDate,
        warningDate,
        DeadlineStatus.WAIVED,
        severity,
        waiverNotes);
  }

  public String getDeadlineCode() {
    return deadlineCode;
  }

  public DeadlineOwnerType getOwnerType() {
    return ownerType;
  }

  public String getOwnerCode() {
    return ownerCode;
  }

  public DeadlineType getType() {
    return type;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public LocalDate getWarningDate() {
    return warningDate;
  }

  public DeadlineStatus getStatus() {
    return status;
  }

  public DeadlineSeverity getSeverity() {
    return severity;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isDueSoon(LocalDate today) {
    return DeadlineRules.isDueSoon(this, today);
  }

  public boolean isOverdue(LocalDate today) {
    return DeadlineRules.isOverdue(this, today);
  }

  public boolean blocksOperationsWhenExpired() {
    return type.blocksOperationsWhenExpired();
  }

  public boolean requiresAttention(LocalDate today) {
    return DeadlineRules.requiresAttention(this, today);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EnterpriseDeadline that)) return false;
    return deadlineCode.equals(that.deadlineCode)
        && ownerType == that.ownerType
        && ownerCode.equals(that.ownerCode)
        && type == that.type
        && dueDate.equals(that.dueDate)
        && Objects.equals(warningDate, that.warningDate)
        && status == that.status
        && severity == that.severity
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        deadlineCode, ownerType, ownerCode, type, dueDate, warningDate, status, severity, notes);
  }
}
