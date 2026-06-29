package it.gabriele.truckflow.domain.warehouse;

import java.util.Set;

/** Baia fisica di carico/scarico, con capacità e vincoli merce. */
public record LoadingDock(
    String dockCode,
    String facilityCode,
    int maxVehicleLengthMeters,
    LoadingDockStatus status,
    Set<String> supportedCargoTags) {

  public LoadingDock {
    dockCode = normalize(dockCode, "Il codice baia è obbligatorio.");
    facilityCode = normalize(facilityCode, "Il codice deposito/magazzino è obbligatorio.");
    if (maxVehicleLengthMeters <= 0) {
      throw new IllegalArgumentException("La lunghezza massima veicolo deve essere positiva.");
    }
    if (status == null) {
      throw new IllegalArgumentException("Lo stato baia è obbligatorio.");
    }
    supportedCargoTags = supportedCargoTags == null ? Set.of() : Set.copyOf(supportedCargoTags);
  }

  public boolean isUsableFor(String cargoTag, int vehicleLengthMeters) {
    if (status != LoadingDockStatus.AVAILABLE && status != LoadingDockStatus.BOOKED) {
      return false;
    }
    if (vehicleLengthMeters > maxVehicleLengthMeters) {
      return false;
    }
    return supportedCargoTags.isEmpty()
        || (cargoTag != null && supportedCargoTags.contains(cargoTag.trim().toUpperCase()));
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
