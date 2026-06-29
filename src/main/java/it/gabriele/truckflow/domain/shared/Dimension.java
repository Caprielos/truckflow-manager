package it.gabriele.truckflow.domain.shared;

import java.util.Objects;

/**
 * Rappresenta le dimensioni fisiche di un oggetto. Internamente lunghezza, larghezza e altezza
 * vengono sempre salvate in metri.
 */
public final class Dimension {

  private static final double CENTIMETERS_PER_METER = 100.0;

  private final double lengthMeters;
  private final double widthMeters;
  private final double heightMeters;

  private Dimension(double lengthMeters, double widthMeters, double heightMeters) {
    validatePositive(lengthMeters, "La lunghezza");
    validatePositive(widthMeters, "La larghezza");
    validatePositive(heightMeters, "L'altezza");

    this.lengthMeters = lengthMeters;
    this.widthMeters = widthMeters;
    this.heightMeters = heightMeters;
  }

  /** Crea una dimensione partendo da valori espressi in metri. */
  public static Dimension ofMeters(double lengthMeters, double widthMeters, double heightMeters) {
    return new Dimension(lengthMeters, widthMeters, heightMeters);
  }

  /**
   * Crea una dimensione partendo da valori espressi in centimetri, convertendoli subito in metri.
   */
  public static Dimension ofCentimeters(
      double lengthCentimeters, double widthCentimeters, double heightCentimeters) {
    return new Dimension(
        lengthCentimeters / CENTIMETERS_PER_METER,
        widthCentimeters / CENTIMETERS_PER_METER,
        heightCentimeters / CENTIMETERS_PER_METER);
  }

  private static void validatePositive(double value, String fieldName) {
    if (value <= 0) {
      throw new IllegalArgumentException(fieldName + " deve essere maggiore di zero.");
    }

    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new IllegalArgumentException(fieldName + " deve essere un numero valido.");
    }
  }

  public double getLengthMeters() {
    return lengthMeters;
  }

  public double getWidthMeters() {
    return widthMeters;
  }

  public double getHeightMeters() {
    return heightMeters;
  }

  /** Calcola il volume occupato da queste dimensioni. */
  public Volume calculateVolume() {
    return Volume.ofCubicMeters(lengthMeters * widthMeters * heightMeters);
  }

  /** Verifica se questa dimensione entra dentro un'altra dimensione. */
  public boolean fitsInside(Dimension container) {
    if (container == null) {
      throw new IllegalArgumentException("La dimensione contenitore è obbligatoria.");
    }

    return this.lengthMeters <= container.lengthMeters
        && this.widthMeters <= container.widthMeters
        && this.heightMeters <= container.heightMeters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Dimension dimension)) return false;
    return Double.compare(lengthMeters, dimension.lengthMeters) == 0
        && Double.compare(widthMeters, dimension.widthMeters) == 0
        && Double.compare(heightMeters, dimension.heightMeters) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(lengthMeters, widthMeters, heightMeters);
  }

  @Override
  public String toString() {
    return lengthMeters + "m x " + widthMeters + "m x " + heightMeters + "m";
  }
}
