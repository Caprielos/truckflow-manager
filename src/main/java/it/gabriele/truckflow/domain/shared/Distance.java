package it.gabriele.truckflow.domain.shared;

import java.util.Objects;

/**
 * Rappresenta una distanza del dominio. Internamente la distanza viene sempre salvata in
 * chilometri.
 */
public final class Distance {

  private static final double METERS_PER_KILOMETER = 1000.0;

  private final double kilometers;

  private Distance(double kilometers) {
    if (kilometers < 0) {
      throw new IllegalArgumentException("La distanza non può essere negativa.");
    }

    if (Double.isNaN(kilometers) || Double.isInfinite(kilometers)) {
      throw new IllegalArgumentException("La distanza deve essere un numero valido.");
    }

    this.kilometers = kilometers;
  }

  /** Crea una distanza partendo dai chilometri. */
  public static Distance ofKilometers(double kilometers) {
    return new Distance(kilometers);
  }

  /** Crea una distanza partendo dai metri, convertendoli subito in chilometri. */
  public static Distance ofMeters(double meters) {
    return new Distance(meters / METERS_PER_KILOMETER);
  }

  public double getKilometers() {
    return kilometers;
  }

  /** Verifica se questa distanza è maggiore di un'altra distanza. */
  public boolean isGreaterThan(Distance other) {
    if (other == null) {
      throw new IllegalArgumentException("La distanza da confrontare è obbligatoria.");
    }

    return this.kilometers > other.kilometers;
  }

  /** Verifica se questa distanza è minore o uguale a un'altra distanza. */
  public boolean isLessThanOrEqualTo(Distance other) {
    if (other == null) {
      throw new IllegalArgumentException("La distanza da confrontare è obbligatoria.");
    }

    return this.kilometers <= other.kilometers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Distance distance)) return false;
    return Double.compare(kilometers, distance.kilometers) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(kilometers);
  }

  @Override
  public String toString() {
    return kilometers + " km";
  }
}
