package it.gabriele.truckflow.domain.fleet;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Composizione dell'allestimento reale: base + accessori + caratteristiche. */
public final class VehicleBodyConfiguration {

  private final VehicleBodyBaseType baseType;
  private final List<VehicleLoadingEquipment> loadingEquipment;
  private final Set<VehicleTechnicalFeature> technicalFeatures;

  private VehicleBodyConfiguration(
      VehicleBodyBaseType baseType,
      List<VehicleLoadingEquipment> loadingEquipment,
      Set<VehicleTechnicalFeature> technicalFeatures) {
    if (baseType == null) {
      throw new IllegalArgumentException("L'allestimento base è obbligatorio.");
    }
    if (loadingEquipment == null) {
      throw new IllegalArgumentException("La lista accessori è obbligatoria.");
    }
    if (loadingEquipment.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("La lista accessori non può contenere valori nulli.");
    }
    if (technicalFeatures == null) {
      throw new IllegalArgumentException("Le caratteristiche tecniche sono obbligatorie.");
    }
    if (technicalFeatures.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "Le caratteristiche tecniche non possono contenere valori nulli.");
    }
    this.baseType = baseType;
    this.loadingEquipment = List.copyOf(loadingEquipment);
    this.technicalFeatures = Set.copyOf(technicalFeatures);
  }

  public static VehicleBodyConfiguration of(
      VehicleBodyBaseType baseType,
      List<VehicleLoadingEquipment> loadingEquipment,
      Set<VehicleTechnicalFeature> technicalFeatures) {
    return new VehicleBodyConfiguration(baseType, loadingEquipment, technicalFeatures);
  }

  public static VehicleBodyConfiguration baseOnly(VehicleBodyBaseType baseType) {
    return new VehicleBodyConfiguration(baseType, List.of(), Set.of());
  }

  public static VehicleBodyConfiguration none() {
    return baseOnly(VehicleBodyBaseType.NONE);
  }

  public VehicleBodyBaseType getBaseType() {
    return baseType;
  }

  public List<VehicleLoadingEquipment> getLoadingEquipment() {
    return loadingEquipment;
  }

  public Set<VehicleTechnicalFeature> getTechnicalFeatures() {
    return technicalFeatures;
  }

  public boolean hasEquipment(VehicleLoadingEquipmentType equipmentType) {
    if (equipmentType == null) {
      throw new IllegalArgumentException("Il tipo accessorio è obbligatorio.");
    }
    return loadingEquipment.stream().anyMatch(equipment -> equipment.isType(equipmentType));
  }

  public boolean hasFeature(VehicleTechnicalFeature feature) {
    if (feature == null) {
      throw new IllegalArgumentException("La caratteristica tecnica è obbligatoria.");
    }
    return technicalFeatures.contains(feature);
  }

  public boolean supportsTemperatureControl() {
    return baseType.supportsTemperatureControl()
        || hasFeature(VehicleTechnicalFeature.ACTIVE_REFRIGERATION)
        || hasFeature(VehicleTechnicalFeature.ATP_CERTIFIED);
  }

  public boolean isCargoBody() {
    return baseType.isCargoBody();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VehicleBodyConfiguration that)) return false;
    return baseType == that.baseType
        && loadingEquipment.equals(that.loadingEquipment)
        && technicalFeatures.equals(that.technicalFeatures);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, loadingEquipment, technicalFeatures);
  }
}
