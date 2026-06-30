package it.gabriele.truckflow.domain.cargo;

public record CargoProperties(
    boolean fragile,
    boolean perishable,
    boolean dangerous,
    boolean highValue,
    boolean requiresSeparation,
    String notes) {

  public CargoProperties {
    notes = CargoValidation.normalize(notes);
  }

  public static CargoProperties standard() {
    return new CargoProperties(false, false, false, false, false, "");
  }
}
