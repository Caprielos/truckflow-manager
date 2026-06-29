package it.gabriele.truckflow.domain.parking;

import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/** Posto fisico numerato dentro un deposito, piazzale o parcheggio. */
public final class ParkingSpot {

  private static final int MAX_CODE_LENGTH = 50;

  private final String facilityCode;
  private final String spotNumber;
  private final ParkingSpotType type;
  private final ParkingSpotStatus status;
  private final double maxLengthMeters;
  private final double maxWidthMeters;
  private final boolean powerSupplyAvailable;
  private final Notes notes;

  private ParkingSpot(
      String facilityCode,
      String spotNumber,
      ParkingSpotType type,
      ParkingSpotStatus status,
      double maxLengthMeters,
      double maxWidthMeters,
      boolean powerSupplyAvailable,
      Notes notes) {
    this.facilityCode =
        validateCode(facilityCode, "Il codice struttura del posto parcheggio è obbligatorio.");
    this.spotNumber = validateCode(spotNumber, "Il numero posto parcheggio è obbligatorio.");
    this.type = validateType(type);
    this.status = validateStatus(status);
    this.maxLengthMeters =
        validatePositiveMeasure(
            maxLengthMeters, "La lunghezza massima posto parcheggio deve essere positiva.");
    this.maxWidthMeters =
        validatePositiveMeasure(
            maxWidthMeters, "La larghezza massima posto parcheggio deve essere positiva.");
    this.powerSupplyAvailable = powerSupplyAvailable;
    this.notes = validateNotes(notes);
  }

  public static ParkingSpot of(
      String facilityCode,
      String spotNumber,
      ParkingSpotType type,
      ParkingSpotStatus status,
      double maxLengthMeters,
      double maxWidthMeters,
      boolean powerSupplyAvailable,
      Notes notes) {
    return new ParkingSpot(
        facilityCode,
        spotNumber,
        type,
        status,
        maxLengthMeters,
        maxWidthMeters,
        powerSupplyAvailable,
        notes);
  }

  public static ParkingSpot available(
      String facilityCode,
      String spotNumber,
      ParkingSpotType type,
      double maxLengthMeters,
      double maxWidthMeters,
      boolean powerSupplyAvailable,
      Notes notes) {
    return new ParkingSpot(
        facilityCode,
        spotNumber,
        type,
        ParkingSpotStatus.AVAILABLE,
        maxLengthMeters,
        maxWidthMeters,
        powerSupplyAvailable,
        notes);
  }

  public static ParkingSpot occupied(
      String facilityCode,
      String spotNumber,
      ParkingSpotType type,
      double maxLengthMeters,
      double maxWidthMeters,
      boolean powerSupplyAvailable,
      Notes notes) {
    return new ParkingSpot(
        facilityCode,
        spotNumber,
        type,
        ParkingSpotStatus.OCCUPIED,
        maxLengthMeters,
        maxWidthMeters,
        powerSupplyAvailable,
        notes);
  }

  private static String validateCode(String code, String message) {
    if (code == null) {
      throw new IllegalArgumentException(message);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(message);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice parcheggio non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice parcheggio può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  private static ParkingSpotType validateType(ParkingSpotType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo posto parcheggio è obbligatorio.");
    }
    return type;
  }

  private static ParkingSpotStatus validateStatus(ParkingSpotStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("Lo stato posto parcheggio è obbligatorio.");
    }
    return status;
  }

  private static double validatePositiveMeasure(double value, String message) {
    if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
      throw new IllegalArgumentException(message);
    }
    return value;
  }

  private static Notes validateNotes(Notes notes) {
    if (notes == null) {
      throw new IllegalArgumentException("Le note posto parcheggio sono obbligatorie.");
    }
    return notes;
  }

  public String getFacilityCode() {
    return facilityCode;
  }

  public String getSpotNumber() {
    return spotNumber;
  }

  public ParkingSpotType getType() {
    return type;
  }

  public ParkingSpotStatus getStatus() {
    return status;
  }

  public double getMaxLengthMeters() {
    return maxLengthMeters;
  }

  public double getMaxWidthMeters() {
    return maxWidthMeters;
  }

  public boolean isPowerSupplyAvailable() {
    return powerSupplyAvailable;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isAvailable() {
    return status.canReceiveNewAssignment();
  }

  public boolean technicallyFits(ParkedResource resource) {
    if (resource == null) {
      throw new IllegalArgumentException("La risorsa da parcheggiare è obbligatoria.");
    }

    return type.canHost(resource.getType()) && resource.getTotalLengthMeters() <= maxLengthMeters;
  }

  public boolean canReceive(ParkedResource resource) {
    return isAvailable() && technicallyFits(resource);
  }

  public boolean isSamePhysicalSpot(String facilityCode, String spotNumber) {
    return this.facilityCode.equals(
            validateCode(facilityCode, "Il codice struttura da confrontare è obbligatorio."))
        && this.spotNumber.equals(
            validateCode(spotNumber, "Il numero posto da confrontare è obbligatorio."));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ParkingSpot that)) return false;
    return Double.compare(maxLengthMeters, that.maxLengthMeters) == 0
        && Double.compare(maxWidthMeters, that.maxWidthMeters) == 0
        && powerSupplyAvailable == that.powerSupplyAvailable
        && facilityCode.equals(that.facilityCode)
        && spotNumber.equals(that.spotNumber)
        && type == that.type
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        facilityCode,
        spotNumber,
        type,
        status,
        maxLengthMeters,
        maxWidthMeters,
        powerSupplyAvailable,
        notes);
  }

  @Override
  public String toString() {
    return facilityCode + " / " + spotNumber + " - " + type + " - " + status;
  }
}
