package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;
import java.util.Objects;

/** Rappresenta un singolo collo o articolo da trasportare. */
public final class CargoItem {

  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String description;
  private final CargoCategory category;
  private final Weight weight;
  private final Dimension dimension;
  private final TemperatureRange requiredTemperatureRange;
  private final DangerousGoodsProfile dangerousGoodsProfile;
  private final Notes notes;

  private CargoItem(
      String description,
      CargoCategory category,
      Weight weight,
      Dimension dimension,
      TemperatureRange requiredTemperatureRange,
      DangerousGoodsProfile dangerousGoodsProfile,
      Notes notes) {
    this.description = validateDescription(description);

    if (category == null) {
      throw new IllegalArgumentException("La categoria del carico è obbligatoria.");
    }

    if (weight == null) {
      throw new IllegalArgumentException("Il peso del carico è obbligatorio.");
    }

    if (dimension == null) {
      throw new IllegalArgumentException("Le dimensioni del carico sono obbligatorie.");
    }

    if (category.requiresTemperatureControl() && requiredTemperatureRange == null) {
      throw new IllegalArgumentException(
          "Questa categoria di carico richiede una temperatura controllata.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note del carico sono obbligatorie.");
    }

    this.category = category;
    this.weight = weight;
    this.dimension = dimension;
    this.requiredTemperatureRange = requiredTemperatureRange;
    this.dangerousGoodsProfile = dangerousGoodsProfile;
    this.notes = notes;
  }

  public static CargoItem of(
      String description, CargoCategory category, Weight weight, Dimension dimension, Notes notes) {
    return new CargoItem(description, category, weight, dimension, null, null, notes);
  }

  public static CargoItem temperatureControlled(
      String description,
      CargoCategory category,
      Weight weight,
      Dimension dimension,
      TemperatureRange requiredTemperatureRange,
      Notes notes) {
    return new CargoItem(
        description, category, weight, dimension, requiredTemperatureRange, null, notes);
  }

  public static CargoItem dangerousGoods(
      String description,
      CargoCategory category,
      Weight weight,
      Dimension dimension,
      DangerousGoodsProfile dangerousGoodsProfile,
      Notes notes) {
    if (dangerousGoodsProfile == null) {
      throw new IllegalArgumentException("Il profilo ADR della merce pericolosa è obbligatorio.");
    }

    return new CargoItem(
        description, category, weight, dimension, null, dangerousGoodsProfile, notes);
  }

  public static CargoItem temperatureControlledDangerousGoods(
      String description,
      CargoCategory category,
      Weight weight,
      Dimension dimension,
      TemperatureRange requiredTemperatureRange,
      DangerousGoodsProfile dangerousGoodsProfile,
      Notes notes) {
    if (dangerousGoodsProfile == null) {
      throw new IllegalArgumentException("Il profilo ADR della merce pericolosa è obbligatorio.");
    }

    return new CargoItem(
        description,
        category,
        weight,
        dimension,
        requiredTemperatureRange,
        dangerousGoodsProfile,
        notes);
  }

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione del carico è obbligatoria.");
    }

    String normalizedDescription = description.trim();

    if (normalizedDescription.isEmpty()) {
      throw new IllegalArgumentException("La descrizione del carico non può essere vuota.");
    }

    if (normalizedDescription.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione del carico non può superare " + MAX_DESCRIPTION_LENGTH + " caratteri.");
    }

    return normalizedDescription;
  }

  public String getDescription() {
    return description;
  }

  public CargoCategory getCategory() {
    return category;
  }

  public Weight getWeight() {
    return weight;
  }

  public Dimension getDimension() {
    return dimension;
  }

  public TemperatureRange getRequiredTemperatureRange() {
    return requiredTemperatureRange;
  }

  public DangerousGoodsProfile getDangerousGoodsProfile() {
    return dangerousGoodsProfile;
  }

  public Notes getNotes() {
    return notes;
  }

  public Volume calculateVolume() {
    return dimension.calculateVolume();
  }

  public boolean requiresTemperatureControl() {
    return requiredTemperatureRange != null;
  }

  public boolean hasDangerousGoodsProfile() {
    return dangerousGoodsProfile != null;
  }

  public boolean isDangerousGoods() {
    return category == CargoCategory.HAZARDOUS_MATERIAL || dangerousGoodsProfile != null;
  }

  public boolean requiresAdrTransport() {
    return dangerousGoodsProfile != null;
  }

  public boolean requiresAdrTankTransport() {
    return dangerousGoodsProfile != null && dangerousGoodsProfile.requiresTankTransport();
  }

  public boolean isAdrClass(AdrClass adrClass) {
    if (adrClass == null) {
      throw new IllegalArgumentException("La classe ADR da verificare è obbligatoria.");
    }

    return dangerousGoodsProfile != null && dangerousGoodsProfile.getAdrClass() == adrClass;
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public String formatSingleLine() {
    if (dangerousGoodsProfile == null) {
      return description + " - " + category + " - " + weight;
    }

    return description
        + " - "
        + category
        + " - "
        + weight
        + " - "
        + dangerousGoodsProfile.getUnNumber();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CargoItem cargoItem)) return false;
    return description.equals(cargoItem.description)
        && category == cargoItem.category
        && weight.equals(cargoItem.weight)
        && dimension.equals(cargoItem.dimension)
        && Objects.equals(requiredTemperatureRange, cargoItem.requiredTemperatureRange)
        && Objects.equals(dangerousGoodsProfile, cargoItem.dangerousGoodsProfile)
        && notes.equals(cargoItem.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        description,
        category,
        weight,
        dimension,
        requiredTemperatureRange,
        dangerousGoodsProfile,
        notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
