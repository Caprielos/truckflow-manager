package it.gabriele.truckflow.domain.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Rappresenta una percentuale del dominio. Il valore deve essere compreso tra 0 e 100. */
public final class Percentage {

  private static final BigDecimal MIN_VALUE = BigDecimal.ZERO;
  private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(100);
  private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

  private final BigDecimal value;

  private Percentage(BigDecimal value) {
    if (value == null) {
      throw new IllegalArgumentException("La percentuale è obbligatoria.");
    }

    if (value.compareTo(MIN_VALUE) < 0) {
      throw new IllegalArgumentException("La percentuale non può essere negativa.");
    }

    if (value.compareTo(MAX_VALUE) > 0) {
      throw new IllegalArgumentException("La percentuale non può essere maggiore di 100.");
    }

    this.value = value;
  }

  public static Percentage of(BigDecimal value) {
    return new Percentage(value);
  }

  public static Percentage of(String value) {
    return new Percentage(new BigDecimal(value));
  }

  public BigDecimal getValue() {
    return value;
  }

  /** Converte la percentuale in moltiplicatore decimale. Esempio: 25% diventa 0.25. */
  public BigDecimal toMultiplier() {
    return value.divide(ONE_HUNDRED, 10, RoundingMode.HALF_UP);
  }

  /** Verifica se questa percentuale è maggiore di un'altra. */
  public boolean isGreaterThan(Percentage other) {
    if (other == null) {
      throw new IllegalArgumentException("La percentuale da confrontare è obbligatoria.");
    }

    return this.value.compareTo(other.value) > 0;
  }

  /** Verifica se questa percentuale è minore o uguale a un'altra. */
  public boolean isLessThanOrEqualTo(Percentage other) {
    if (other == null) {
      throw new IllegalArgumentException("La percentuale da confrontare è obbligatoria.");
    }

    return this.value.compareTo(other.value) <= 0;
  }

  private BigDecimal normalizedValue() {
    return value.stripTrailingZeros();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Percentage that)) return false;
    return normalizedValue().compareTo(that.normalizedValue()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(normalizedValue());
  }

  @Override
  public String toString() {
    return value + "%";
  }
}
