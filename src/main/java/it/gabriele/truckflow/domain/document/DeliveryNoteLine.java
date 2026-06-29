package it.gabriele.truckflow.domain.document;

import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/** Riga interna di una bolla/DDT: merce, colli, peso, volume e pallet. */
public final class DeliveryNoteLine {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 250;

  private final String lineCode;
  private final String description;
  private final int packagesCount;
  private final double grossWeightKilograms;
  private final double volumeCubicMeters;
  private final int palletCount;
  private final Notes notes;

  private DeliveryNoteLine(
      String lineCode,
      String description,
      int packagesCount,
      double grossWeightKilograms,
      double volumeCubicMeters,
      int palletCount,
      Notes notes) {
    this.lineCode = validateCode(lineCode);
    this.description = validateDescription(description);
    if (packagesCount <= 0) {
      throw new IllegalArgumentException("Il numero colli bolla deve essere maggiore di zero.");
    }
    validateNonNegative(grossWeightKilograms, "Il peso lordo bolla");
    validateNonNegative(volumeCubicMeters, "Il volume bolla");
    if (palletCount < 0) {
      throw new IllegalArgumentException("Il numero pallet non può essere negativo.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note riga bolla sono obbligatorie.");
    }
    this.packagesCount = packagesCount;
    this.grossWeightKilograms = grossWeightKilograms;
    this.volumeCubicMeters = volumeCubicMeters;
    this.palletCount = palletCount;
    this.notes = notes;
  }

  public static DeliveryNoteLine of(
      String lineCode,
      String description,
      int packagesCount,
      double grossWeightKilograms,
      double volumeCubicMeters,
      int palletCount,
      Notes notes) {
    return new DeliveryNoteLine(
        lineCode,
        description,
        packagesCount,
        grossWeightKilograms,
        volumeCubicMeters,
        palletCount,
        notes);
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice riga bolla è obbligatorio.");
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Il codice riga bolla non può essere vuoto.");
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice riga bolla non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice riga bolla può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione riga bolla è obbligatoria.");
    }
    String normalized = description.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("La descrizione riga bolla non può essere vuota.");
    }
    if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione riga bolla non può superare " + MAX_DESCRIPTION_LENGTH + " caratteri.");
    }
    return normalized;
  }

  private static void validateNonNegative(double value, String fieldName) {
    if (value < 0 || Double.isNaN(value) || Double.isInfinite(value)) {
      throw new IllegalArgumentException(fieldName + " deve essere un numero valido non negativo.");
    }
  }

  public String getLineCode() {
    return lineCode;
  }

  public String getDescription() {
    return description;
  }

  public int getPackagesCount() {
    return packagesCount;
  }

  public double getGrossWeightKilograms() {
    return grossWeightKilograms;
  }

  public double getVolumeCubicMeters() {
    return volumeCubicMeters;
  }

  public int getPalletCount() {
    return palletCount;
  }

  public Notes getNotes() {
    return notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DeliveryNoteLine that)) return false;
    return packagesCount == that.packagesCount
        && Double.compare(grossWeightKilograms, that.grossWeightKilograms) == 0
        && Double.compare(volumeCubicMeters, that.volumeCubicMeters) == 0
        && palletCount == that.palletCount
        && lineCode.equals(that.lineCode)
        && description.equals(that.description)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        lineCode,
        description,
        packagesCount,
        grossWeightKilograms,
        volumeCubicMeters,
        palletCount,
        notes);
  }
}
