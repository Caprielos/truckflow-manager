package it.gabriele.truckflow.domain.shared;

import java.util.Objects;

/** Rappresenta un volume del dominio. Internamente il volume viene sempre salvato in metri cubi. */
public final class Volume {

  private static final double LITERS_PER_CUBIC_METER = 1000.0;

  private final double cubicMeters;

  private Volume(double cubicMeters) {
    if (cubicMeters < 0) {
      throw new IllegalArgumentException("Il volume non può essere negativo.");
    }

    if (Double.isNaN(cubicMeters) || Double.isInfinite(cubicMeters)) {
      throw new IllegalArgumentException("Il volume deve essere un numero valido.");
    }

    this.cubicMeters = cubicMeters;
  }

  /** Crea un volume partendo dai metri cubi. */
  public static Volume ofCubicMeters(double cubicMeters) {
    return new Volume(cubicMeters);
  }

  /** Crea un volume partendo dai litri, convertendoli subito in metri cubi. */
  public static Volume ofLiters(double liters) {
    return new Volume(liters / LITERS_PER_CUBIC_METER);
  }

  public double getCubicMeters() {
    return cubicMeters;
  }

  /** Verifica se questo volume è maggiore di un altro volume. */
  public boolean isGreaterThan(Volume other) {
    if (other == null) {
      throw new IllegalArgumentException("Il volume da confrontare è obbligatorio.");
    }

    return this.cubicMeters > other.cubicMeters;
  }

  /** Verifica se questo volume è minore o uguale a un altro volume. */
  public boolean isLessThanOrEqualTo(Volume other) {
    if (other == null) {
      throw new IllegalArgumentException("Il volume da confrontare è obbligatorio.");
    }

    return this.cubicMeters <= other.cubicMeters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Volume volume)) return false;
    return Double.compare(cubicMeters, volume.cubicMeters) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cubicMeters);
  }

  @Override
  public String toString() {
    return cubicMeters + " m3";
  }
}
