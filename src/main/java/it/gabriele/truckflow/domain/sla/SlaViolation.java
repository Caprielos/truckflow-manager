package it.gabriele.truckflow.domain.sla;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Violazione SLA rilevata su missione, documento, consegna o reclamo. */
public final class SlaViolation {

  private static final int MAX_CODE_LENGTH = 50;

  private final String violationCode;
  private final String agreementCode;
  private final SlaMetric metric;
  private final String referenceCode;
  private final Instant occurredAt;
  private final Money penaltyAmount;
  private final boolean waived;
  private final Notes notes;

  private SlaViolation(
      String violationCode,
      String agreementCode,
      SlaMetric metric,
      String referenceCode,
      Instant occurredAt,
      Money penaltyAmount,
      boolean waived,
      Notes notes) {
    this.violationCode = validateCode(violationCode, "Il codice violazione SLA è obbligatorio.");
    this.agreementCode = validateCode(agreementCode, "Il codice accordo SLA è obbligatorio.");

    if (metric == null) {
      throw new IllegalArgumentException("La metrica violazione SLA è obbligatoria.");
    }

    this.referenceCode =
        validateCode(referenceCode, "Il riferimento violazione SLA è obbligatorio.");

    if (occurredAt == null) {
      throw new IllegalArgumentException("La data violazione SLA è obbligatoria.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note violazione SLA sono obbligatorie.");
    }

    this.metric = metric;
    this.occurredAt = occurredAt;
    this.penaltyAmount = penaltyAmount;
    this.waived = waived;
    this.notes = notes;
  }

  public static SlaViolation detected(
      String violationCode,
      String agreementCode,
      SlaMetric metric,
      String referenceCode,
      Instant occurredAt,
      Money penaltyAmount,
      Notes notes) {
    return new SlaViolation(
        violationCode,
        agreementCode,
        metric,
        referenceCode,
        occurredAt,
        penaltyAmount,
        false,
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

  public SlaViolation waive(Notes waiverNotes) {
    if (!SlaRules.canBeWaived(this)) {
      throw new IllegalStateException("La violazione SLA non può essere annullata.");
    }

    if (waiverNotes == null || waiverNotes.isEmpty()) {
      throw new IllegalArgumentException("Le note annullamento penale sono obbligatorie.");
    }

    return new SlaViolation(
        violationCode,
        agreementCode,
        metric,
        referenceCode,
        occurredAt,
        penaltyAmount,
        true,
        waiverNotes);
  }

  public String getViolationCode() {
    return violationCode;
  }

  public String getAgreementCode() {
    return agreementCode;
  }

  public SlaMetric getMetric() {
    return metric;
  }

  public String getReferenceCode() {
    return referenceCode;
  }

  public Instant getOccurredAt() {
    return occurredAt;
  }

  public Money getPenaltyAmount() {
    return penaltyAmount;
  }

  public boolean isWaived() {
    return waived;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean hasPenalty() {
    return penaltyAmount != null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SlaViolation that)) return false;
    return waived == that.waived
        && violationCode.equals(that.violationCode)
        && agreementCode.equals(that.agreementCode)
        && metric == that.metric
        && referenceCode.equals(that.referenceCode)
        && occurredAt.equals(that.occurredAt)
        && Objects.equals(penaltyAmount, that.penaltyAmount)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        violationCode,
        agreementCode,
        metric,
        referenceCode,
        occurredAt,
        penaltyAmount,
        waived,
        notes);
  }
}
