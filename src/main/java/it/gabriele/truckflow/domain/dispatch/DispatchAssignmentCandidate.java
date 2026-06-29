package it.gabriele.truckflow.domain.dispatch;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Candidato di assegnazione per una missione: autista, mezzo/convoglio, parcheggio pronto e
 * controllo margine.
 */
public final class DispatchAssignmentCandidate {

  private static final int MAX_CODE_LENGTH = 50;

  private final String candidateCode;
  private final String missionNumber;
  private final String driverId;
  private final String vehicleFleetNumber;
  private final String trailerFleetNumber;
  private final String parkedResourceCode;
  private final Money estimatedRevenue;
  private final Money estimatedCost;
  private final List<DispatchCheckResult> checks;
  private final Notes notes;

  private DispatchAssignmentCandidate(
      String candidateCode,
      String missionNumber,
      String driverId,
      String vehicleFleetNumber,
      String trailerFleetNumber,
      String parkedResourceCode,
      Money estimatedRevenue,
      Money estimatedCost,
      List<DispatchCheckResult> checks,
      Notes notes) {
    this.candidateCode =
        validateCode(candidateCode, "Il codice candidato dispatch è obbligatorio.");
    this.missionNumber = validateCode(missionNumber, "Il numero missione dispatch è obbligatorio.");
    this.driverId = validateCode(driverId, "Il codice autista dispatch è obbligatorio.");
    this.vehicleFleetNumber =
        validateCode(vehicleFleetNumber, "Il numero flotta mezzo dispatch è obbligatorio.");
    this.trailerFleetNumber = normalizeOptionalCode(trailerFleetNumber);
    this.parkedResourceCode = normalizeOptionalCode(parkedResourceCode);
    if (estimatedRevenue == null) {
      throw new IllegalArgumentException("Il ricavo stimato candidato dispatch è obbligatorio.");
    }
    if (estimatedCost == null) {
      throw new IllegalArgumentException("Il costo stimato candidato dispatch è obbligatorio.");
    }
    Currency revenueCurrency = estimatedRevenue.getCurrency();
    if (!revenueCurrency.equals(estimatedCost.getCurrency())) {
      throw new IllegalArgumentException("Ricavo e costo stimati devono avere la stessa valuta.");
    }
    this.checks = validateChecks(checks);
    if (notes == null) {
      throw new IllegalArgumentException("Le note candidato dispatch sono obbligatorie.");
    }
    this.estimatedRevenue = estimatedRevenue;
    this.estimatedCost = estimatedCost;
    this.notes = notes;
  }

  public static DispatchAssignmentCandidate of(
      String candidateCode,
      String missionNumber,
      String driverId,
      String vehicleFleetNumber,
      String trailerFleetNumber,
      String parkedResourceCode,
      Money estimatedRevenue,
      Money estimatedCost,
      List<DispatchCheckResult> checks,
      Notes notes) {
    return new DispatchAssignmentCandidate(
        candidateCode,
        missionNumber,
        driverId,
        vehicleFleetNumber,
        trailerFleetNumber,
        parkedResourceCode,
        estimatedRevenue,
        estimatedCost,
        checks,
        notes);
  }

  private static List<DispatchCheckResult> validateChecks(List<DispatchCheckResult> checks) {
    if (checks == null) {
      throw new IllegalArgumentException("I controlli candidato dispatch sono obbligatori.");
    }
    if (checks.isEmpty()) {
      throw new IllegalArgumentException("Il candidato dispatch deve avere almeno un controllo.");
    }
    if (checks.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "I controlli candidato dispatch non possono contenere null.");
    }
    long uniqueTypes = checks.stream().map(DispatchCheckResult::getType).distinct().count();
    if (uniqueTypes != checks.size()) {
      throw new IllegalArgumentException(
          "Il candidato dispatch non può avere controlli duplicati dello stesso tipo.");
    }
    return List.copyOf(checks);
  }

  private static String validateCode(String code, String message) {
    if (code == null) {
      throw new IllegalArgumentException(message);
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static String normalizeOptionalCode(String code) {
    if (code == null || code.trim().isEmpty()) {
      return "";
    }
    return validateCode(code, "Il codice opzionale dispatch non è valido.");
  }

  public String getCandidateCode() {
    return candidateCode;
  }

  public String getMissionNumber() {
    return missionNumber;
  }

  public String getDriverId() {
    return driverId;
  }

  public String getVehicleFleetNumber() {
    return vehicleFleetNumber;
  }

  public String getTrailerFleetNumber() {
    return trailerFleetNumber;
  }

  public String getParkedResourceCode() {
    return parkedResourceCode;
  }

  public Money getEstimatedRevenue() {
    return estimatedRevenue;
  }

  public Money getEstimatedCost() {
    return estimatedCost;
  }

  public List<DispatchCheckResult> getChecks() {
    return checks;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean hasTrailer() {
    return !trailerFleetNumber.isEmpty();
  }

  public boolean isParkedCombinationReady() {
    return !parkedResourceCode.isEmpty()
        && checks.stream()
            .anyMatch(
                check ->
                    check.getType() == DispatchCheckType.PARKING_READY
                        && check.getStatus() == DispatchReadinessStatus.READY);
  }

  public boolean hasBlockingIssue() {
    return checks.stream().anyMatch(DispatchCheckResult::blocksAssignment);
  }

  public boolean requiresManualReview() {
    return checks.stream().anyMatch(DispatchCheckResult::requiresManualReview);
  }

  public boolean isAssignable() {
    return !hasBlockingIssue();
  }

  public BigDecimal calculateGrossMarginAmount() {
    return estimatedRevenue.getAmount().subtract(estimatedCost.getAmount());
  }

  public boolean isProfitExpected() {
    return calculateGrossMarginAmount().signum() > 0;
  }
}
