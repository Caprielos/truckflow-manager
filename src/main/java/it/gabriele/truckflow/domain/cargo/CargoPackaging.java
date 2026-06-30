package it.gabriele.truckflow.domain.cargo;

public record CargoPackaging(
    CargoPackagingType packagingType,
    Integer units,
    Integer pallets,
    String containerType,
    boolean stackable,
    String notes) {

  public CargoPackaging {
    packagingType = CargoValidation.requireNonNull(packagingType, "packagingType");
    units = CargoValidation.nonNegativeOrNull(units, "units");
    pallets = CargoValidation.nonNegativeOrNull(pallets, "pallets");
    containerType = CargoValidation.normalize(containerType).toUpperCase();
    notes = CargoValidation.normalize(notes);
  }

  public static CargoPackaging loose() {
    return new CargoPackaging(CargoPackagingType.LOOSE, null, null, "", false, "");
  }
}
