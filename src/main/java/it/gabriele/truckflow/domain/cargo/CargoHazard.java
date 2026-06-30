package it.gabriele.truckflow.domain.cargo;

public record CargoHazard(
    String adrClass, String unNumber, String packingGroup, String specialProvisions, String notes) {

  public CargoHazard {
    adrClass = CargoValidation.normalize(adrClass).toUpperCase();
    unNumber = CargoValidation.normalize(unNumber).toUpperCase();
    packingGroup = CargoValidation.normalize(packingGroup).toUpperCase();
    specialProvisions = CargoValidation.normalize(specialProvisions);
    notes = CargoValidation.normalize(notes);
  }

  public boolean hasAdrInformation() {
    return !adrClass.isBlank() || !unNumber.isBlank() || !packingGroup.isBlank();
  }

  public static CargoHazard none() {
    return new CargoHazard("", "", "", "", "");
  }
}
