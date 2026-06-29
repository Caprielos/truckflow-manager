package it.gabriele.truckflow.domain.customs;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Attraversamento confine o transito doganale di una missione internazionale. */
public final class BorderCrossing {

  private static final int MAX_CODE_LENGTH = 50;

  private final String crossingCode;
  private final String missionCode;
  private final String countryCode;
  private final Instant plannedAt;
  private final Instant actualAt;
  private final int waitingMinutes;
  private final Notes notes;

  private BorderCrossing(
      String crossingCode,
      String missionCode,
      String countryCode,
      Instant plannedAt,
      Instant actualAt,
      int waitingMinutes,
      Notes notes) {
    this.crossingCode = validateCode(crossingCode, "Il codice attraversamento è obbligatorio.");
    this.missionCode =
        validateCode(missionCode, "Il codice missione attraversamento è obbligatorio.");
    this.countryCode = validateCountryCode(countryCode);

    if (plannedAt == null) {
      throw new IllegalArgumentException("La data pianificata attraversamento è obbligatoria.");
    }

    if (waitingMinutes < 0) {
      throw new IllegalArgumentException("L'attesa doganale non può essere negativa.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note attraversamento sono obbligatorie.");
    }

    this.plannedAt = plannedAt;
    this.actualAt = actualAt;
    this.waitingMinutes = waitingMinutes;
    this.notes = notes;
  }

  public static BorderCrossing planned(
      String crossingCode, String missionCode, String countryCode, Instant plannedAt, Notes notes) {
    return new BorderCrossing(crossingCode, missionCode, countryCode, plannedAt, null, 0, notes);
  }

  public BorderCrossing registerActual(Instant actualAt, int waitingMinutes) {
    if (actualAt == null) {
      throw new IllegalArgumentException("La data reale attraversamento è obbligatoria.");
    }

    return new BorderCrossing(
        crossingCode, missionCode, countryCode, plannedAt, actualAt, waitingMinutes, notes);
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

  private static String validateCountryCode(String countryCode) {
    if (countryCode == null) {
      throw new IllegalArgumentException("Il paese attraversamento è obbligatorio.");
    }

    String normalizedCountryCode = countryCode.trim().toUpperCase();

    if (!normalizedCountryCode.matches("[A-Z]{2}")) {
      throw new IllegalArgumentException(
          "Il codice paese attraversamento deve essere ISO alpha-2.");
    }

    return normalizedCountryCode;
  }

  public String getCrossingCode() {
    return crossingCode;
  }

  public String getMissionCode() {
    return missionCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public Instant getPlannedAt() {
    return plannedAt;
  }

  public Instant getActualAt() {
    return actualAt;
  }

  public int getWaitingMinutes() {
    return waitingMinutes;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean hasActualCrossing() {
    return actualAt != null;
  }

  public boolean hasWaitingTime() {
    return waitingMinutes > 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BorderCrossing that)) return false;
    return waitingMinutes == that.waitingMinutes
        && crossingCode.equals(that.crossingCode)
        && missionCode.equals(that.missionCode)
        && countryCode.equals(that.countryCode)
        && plannedAt.equals(that.plannedAt)
        && Objects.equals(actualAt, that.actualAt)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        crossingCode, missionCode, countryCode, plannedAt, actualAt, waitingMinutes, notes);
  }
}
