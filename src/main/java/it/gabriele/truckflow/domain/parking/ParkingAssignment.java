package it.gabriele.truckflow.domain.parking;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Assegnazione di una risorsa a un posto parcheggio. Può rappresentare anche un convoglio già
 * agganciato e pronto per partire.
 */
public final class ParkingAssignment {

  private static final int MAX_CODE_LENGTH = 50;

  private final String assignmentCode;
  private final String facilityCode;
  private final String spotNumber;
  private final ParkedResource parkedResource;
  private final LocalDateTime startedAt;
  private final LocalDateTime endedAt;
  private final Notes notes;

  private ParkingAssignment(
      String assignmentCode,
      String facilityCode,
      String spotNumber,
      ParkedResource parkedResource,
      LocalDateTime startedAt,
      LocalDateTime endedAt,
      Notes notes) {
    this.assignmentCode =
        validateCode(assignmentCode, "Il codice assegnazione parcheggio è obbligatorio.");
    this.facilityCode =
        validateCode(facilityCode, "Il codice struttura parcheggio è obbligatorio.");
    this.spotNumber = validateCode(spotNumber, "Il numero posto parcheggio è obbligatorio.");
    this.parkedResource = validateResource(parkedResource);
    this.startedAt = validateStartedAt(startedAt);
    this.endedAt = endedAt;
    this.notes = validateNotes(notes);

    if (endedAt != null && endedAt.isBefore(startedAt)) {
      throw new IllegalArgumentException(
          "La fine assegnazione parcheggio non può essere precedente all'inizio.");
    }
  }

  public static ParkingAssignment active(
      String assignmentCode,
      ParkingSpot spot,
      ParkedResource parkedResource,
      LocalDateTime startedAt,
      Notes notes) {
    if (spot == null) {
      throw new IllegalArgumentException("Il posto parcheggio è obbligatorio.");
    }
    if (!spot.canReceive(parkedResource)) {
      throw new IllegalArgumentException("Il posto parcheggio non può ricevere questa risorsa.");
    }
    return new ParkingAssignment(
        assignmentCode,
        spot.getFacilityCode(),
        spot.getSpotNumber(),
        parkedResource,
        startedAt,
        null,
        notes);
  }

  public static ParkingAssignment closed(
      String assignmentCode,
      String facilityCode,
      String spotNumber,
      ParkedResource parkedResource,
      LocalDateTime startedAt,
      LocalDateTime endedAt,
      Notes notes) {
    if (endedAt == null) {
      throw new IllegalArgumentException("La data fine assegnazione chiusa è obbligatoria.");
    }
    return new ParkingAssignment(
        assignmentCode, facilityCode, spotNumber, parkedResource, startedAt, endedAt, notes);
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

  private static ParkedResource validateResource(ParkedResource parkedResource) {
    if (parkedResource == null) {
      throw new IllegalArgumentException("La risorsa parcheggiata è obbligatoria.");
    }
    return parkedResource;
  }

  private static LocalDateTime validateStartedAt(LocalDateTime startedAt) {
    if (startedAt == null) {
      throw new IllegalArgumentException("La data inizio assegnazione parcheggio è obbligatoria.");
    }
    return startedAt;
  }

  private static Notes validateNotes(Notes notes) {
    if (notes == null) {
      throw new IllegalArgumentException("Le note assegnazione parcheggio sono obbligatorie.");
    }
    return notes;
  }

  public String getAssignmentCode() {
    return assignmentCode;
  }

  public String getFacilityCode() {
    return facilityCode;
  }

  public String getSpotNumber() {
    return spotNumber;
  }

  public ParkedResource getParkedResource() {
    return parkedResource;
  }

  public LocalDateTime getStartedAt() {
    return startedAt;
  }

  public Optional<LocalDateTime> getEndedAt() {
    return Optional.ofNullable(endedAt);
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isActive() {
    return endedAt == null;
  }

  public boolean isActiveAt(LocalDateTime moment) {
    if (moment == null) {
      throw new IllegalArgumentException("Il momento da verificare è obbligatorio.");
    }
    return !moment.isBefore(startedAt) && (endedAt == null || !moment.isAfter(endedAt));
  }

  public boolean parksCombination() {
    return parkedResource.isCombination();
  }

  public boolean isReadyForMission() {
    return isActive() && parkedResource.isReadyForMission();
  }

  public boolean occupiesSpot(String facilityCode, String spotNumber) {
    return this.facilityCode.equals(
            validateCode(facilityCode, "Il codice struttura da verificare è obbligatorio."))
        && this.spotNumber.equals(
            validateCode(spotNumber, "Il numero posto da verificare è obbligatorio."));
  }

  public boolean containsResource(String resourceId) {
    return parkedResource.includesResource(resourceId)
        || parkedResource
            .getResourceId()
            .equals(validateCode(resourceId, "Il codice risorsa da verificare è obbligatorio."));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ParkingAssignment that)) return false;
    return assignmentCode.equals(that.assignmentCode)
        && facilityCode.equals(that.facilityCode)
        && spotNumber.equals(that.spotNumber)
        && parkedResource.equals(that.parkedResource)
        && startedAt.equals(that.startedAt)
        && Objects.equals(endedAt, that.endedAt)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        assignmentCode, facilityCode, spotNumber, parkedResource, startedAt, endedAt, notes);
  }

  @Override
  public String toString() {
    return assignmentCode + " - " + facilityCode + "/" + spotNumber + " - " + parkedResource;
  }
}
