package it.gabriele.truckflow.domain.cargo;

public record CargoRegulatory(
    boolean adrRequired,
    boolean atpRequired,
    boolean foodGradeRequired,
    boolean pharmaGradeRequired,
    boolean wasteAuthorizationRequired,
    boolean livestockAuthorizationRequired,
    String notes) {

  public CargoRegulatory {
    notes = CargoValidation.normalize(notes);
  }

  public boolean hasSpecialRequirement() {
    return adrRequired
        || atpRequired
        || foodGradeRequired
        || pharmaGradeRequired
        || wasteAuthorizationRequired
        || livestockAuthorizationRequired;
  }

  public static CargoRegulatory none() {
    return new CargoRegulatory(false, false, false, false, false, false, "");
  }
}
