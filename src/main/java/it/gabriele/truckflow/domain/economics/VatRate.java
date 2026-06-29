package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Percentage;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Aliquota IVA usata in una riga economica. Sono presenti factory comode per le aliquote italiane
 * più comuni, ma il valore resta configurabile.
 */
public final class VatRate {

  private static final int MAX_CODE_LENGTH = 30;

  private final String code;
  private final Percentage percentage;

  private VatRate(String code, Percentage percentage) {
    this.code = validateCode(code);
    if (percentage == null) {
      throw new IllegalArgumentException("La percentuale IVA è obbligatoria.");
    }
    this.percentage = percentage;
  }

  public static VatRate of(String code, Percentage percentage) {
    return new VatRate(code, percentage);
  }

  public static VatRate of(String code, String percentage) {
    return new VatRate(code, Percentage.of(percentage));
  }

  public static VatRate italianStandard22() {
    return of("IT_IVA_22", "22");
  }

  public static VatRate italianReduced10() {
    return of("IT_IVA_10", "10");
  }

  public static VatRate italianReduced5() {
    return of("IT_IVA_5", "5");
  }

  public static VatRate italianReduced4() {
    return of("IT_IVA_4", "4");
  }

  public static VatRate zero(String code) {
    return of(code, Percentage.of(BigDecimal.ZERO));
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice aliquota IVA è obbligatorio.");
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Il codice aliquota IVA non può essere vuoto.");
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice aliquota IVA non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_%-]+")) {
      throw new IllegalArgumentException(
          "Il codice aliquota IVA può contenere solo lettere, numeri, trattini, underscore e"
              + " percentuale.");
    }
    return normalized;
  }

  public String getCode() {
    return code;
  }

  public Percentage getPercentage() {
    return percentage;
  }

  public BigDecimal toMultiplier() {
    return percentage.toMultiplier();
  }

  public boolean isZero() {
    return percentage.getValue().signum() == 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VatRate vatRate)) return false;
    return code.equals(vatRate.code) && percentage.equals(vatRate.percentage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, percentage);
  }

  @Override
  public String toString() {
    return code + " (" + percentage + ")";
  }
}
