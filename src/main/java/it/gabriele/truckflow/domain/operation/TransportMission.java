package it.gabriele.truckflow.domain.operation;

import it.gabriele.truckflow.domain.compliance.ComplianceRules;
import it.gabriele.truckflow.domain.driver.Driver;
import it.gabriele.truckflow.domain.fleet.VehicleCombination;
import it.gabriele.truckflow.domain.route.RoutePlan;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shipment.Shipment;
import java.util.Objects;

/**
 * Rappresenta la missione operativa reale. Una missione collega una spedizione con autista, mezzo e
 * piano di tratta.
 */
public final class TransportMission {

  private static final int MAX_MISSION_NUMBER_LENGTH = 50;

  private final String missionNumber;
  private final Shipment shipment;
  private final Driver driver;
  private final VehicleCombination vehicleCombination;
  private final RoutePlan routePlan;
  private final TransportMissionStatus status;
  private final Notes notes;

  private TransportMission(
      String missionNumber,
      Shipment shipment,
      Driver driver,
      VehicleCombination vehicleCombination,
      RoutePlan routePlan,
      TransportMissionStatus status,
      Notes notes) {
    this.missionNumber = validateMissionNumber(missionNumber);

    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione della missione è obbligatoria.");
    }

    if (driver == null) {
      throw new IllegalArgumentException("L'autista della missione è obbligatorio.");
    }

    if (vehicleCombination == null) {
      throw new IllegalArgumentException(
          "La combinazione veicolare della missione è obbligatoria.");
    }

    if (routePlan == null) {
      throw new IllegalArgumentException("Il piano di tratta della missione è obbligatorio.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato della missione è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note della missione sono obbligatorie.");
    }

    this.shipment = shipment;
    this.driver = driver;
    this.vehicleCombination = vehicleCombination;
    this.routePlan = routePlan;
    this.status = status;
    this.notes = notes;
  }

  public static TransportMission planned(
      String missionNumber,
      Shipment shipment,
      Driver driver,
      VehicleCombination vehicleCombination,
      RoutePlan routePlan,
      Notes notes) {
    if (!ComplianceRules.isAssignmentCompliant(driver, vehicleCombination, routePlan, shipment)) {
      throw new IllegalArgumentException(
          "La missione non può essere pianificata perché l'assegnazione non è conforme.");
    }

    return new TransportMission(
        missionNumber,
        shipment,
        driver,
        vehicleCombination,
        routePlan,
        TransportMissionStatus.PLANNED,
        notes);
  }

  private static String validateMissionNumber(String missionNumber) {
    if (missionNumber == null) {
      throw new IllegalArgumentException("Il numero missione è obbligatorio.");
    }

    String normalizedMissionNumber = missionNumber.trim().toUpperCase();

    if (normalizedMissionNumber.isEmpty()) {
      throw new IllegalArgumentException("Il numero missione non può essere vuoto.");
    }

    if (normalizedMissionNumber.length() > MAX_MISSION_NUMBER_LENGTH) {
      throw new IllegalArgumentException(
          "Il numero missione non può superare " + MAX_MISSION_NUMBER_LENGTH + " caratteri.");
    }

    if (!normalizedMissionNumber.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il numero missione può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedMissionNumber;
  }

  public TransportMission dispatch() {
    if (!TransportMissionRules.canBeDispatched(this)) {
      throw new IllegalStateException("La missione non può essere inviata.");
    }

    return new TransportMission(
        missionNumber,
        shipment,
        driver,
        vehicleCombination,
        routePlan,
        TransportMissionStatus.DISPATCHED,
        notes);
  }

  public TransportMission start() {
    if (!TransportMissionRules.canBeStarted(this)) {
      throw new IllegalStateException("La missione non può essere avviata.");
    }

    return new TransportMission(
        missionNumber,
        shipment,
        driver,
        vehicleCombination,
        routePlan,
        TransportMissionStatus.IN_PROGRESS,
        notes);
  }

  public TransportMission complete() {
    if (!TransportMissionRules.canBeCompleted(this)) {
      throw new IllegalStateException("La missione non può essere completata.");
    }

    return new TransportMission(
        missionNumber,
        shipment,
        driver,
        vehicleCombination,
        routePlan,
        TransportMissionStatus.COMPLETED,
        notes);
  }

  public TransportMission cancel() {
    if (!TransportMissionRules.canBeCancelled(this)) {
      throw new IllegalStateException("La missione non può essere cancellata.");
    }

    return new TransportMission(
        missionNumber,
        shipment,
        driver,
        vehicleCombination,
        routePlan,
        TransportMissionStatus.CANCELLED,
        notes);
  }

  public String getMissionNumber() {
    return missionNumber;
  }

  public Shipment getShipment() {
    return shipment;
  }

  public Driver getDriver() {
    return driver;
  }

  public VehicleCombination getVehicleCombination() {
    return vehicleCombination;
  }

  public RoutePlan getRoutePlan() {
    return routePlan;
  }

  public TransportMissionStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isPlanned() {
    return status == TransportMissionStatus.PLANNED;
  }

  public boolean isDispatched() {
    return status == TransportMissionStatus.DISPATCHED;
  }

  public boolean isInProgress() {
    return status == TransportMissionStatus.IN_PROGRESS;
  }

  public boolean isCompleted() {
    return status == TransportMissionStatus.COMPLETED;
  }

  public boolean isCancelled() {
    return status == TransportMissionStatus.CANCELLED;
  }

  public boolean isTerminal() {
    return status.isTerminal();
  }

  public boolean isInternational() {
    return shipment.isInternational();
  }

  public boolean requiresTemperatureControlledTransport() {
    return shipment.requiresTemperatureControlledTransport();
  }

  public boolean containsHazardousMaterial() {
    return shipment.containsHazardousMaterial();
  }

  public boolean requiresSpecialComplianceChecks() {
    return ComplianceRules.requiresSpecialComplianceChecks(shipment);
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public String formatSingleLine() {
    return missionNumber
        + " - shipment: "
        + shipment.getShipmentNumber()
        + " - driver: "
        + driver.getDriverCode()
        + " - vehicle: "
        + vehicleCombination.getCombinationNumber()
        + " - "
        + status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TransportMission that)) return false;
    return missionNumber.equals(that.missionNumber)
        && shipment.equals(that.shipment)
        && driver.equals(that.driver)
        && vehicleCombination.equals(that.vehicleCombination)
        && routePlan.equals(that.routePlan)
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        missionNumber, shipment, driver, vehicleCombination, routePlan, status, notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
