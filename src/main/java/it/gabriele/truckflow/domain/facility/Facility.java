package it.gabriele.truckflow.domain.facility;

import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Rappresenta una struttura fisica usata nelle operazioni logistiche. Esempio: magazzino, deposito,
 * terminal, punto cliente o fornitore.
 */
public final class Facility {

  private static final int MAX_CODE_LENGTH = 50;

  private final String code;
  private final FacilityType type;
  private final Location location;
  private final TimeWindow operatingHours;
  private final Notes notes;
  private final boolean active;

  private Facility(
      String code,
      FacilityType type,
      Location location,
      TimeWindow operatingHours,
      Notes notes,
      boolean active) {
    this.code = validateCode(code);

    if (type == null) {
      throw new IllegalArgumentException("Il tipo di struttura è obbligatorio.");
    }

    if (location == null) {
      throw new IllegalArgumentException("Il luogo della struttura è obbligatorio.");
    }

    if (operatingHours == null) {
      throw new IllegalArgumentException("Gli orari operativi sono obbligatori.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note della struttura sono obbligatorie.");
    }

    this.type = type;
    this.location = location;
    this.operatingHours = operatingHours;
    this.notes = notes;
    this.active = active;
  }

  public static Facility active(
      String code, FacilityType type, Location location, TimeWindow operatingHours, Notes notes) {
    return new Facility(code, type, location, operatingHours, notes, true);
  }

  public static Facility inactive(
      String code, FacilityType type, Location location, TimeWindow operatingHours, Notes notes) {
    return new Facility(code, type, location, operatingHours, notes, false);
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice della struttura è obbligatorio.");
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException("Il codice della struttura non può essere vuoto.");
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice della struttura non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice della struttura può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public String getCode() {
    return code;
  }

  public FacilityType getType() {
    return type;
  }

  public Location getLocation() {
    return location;
  }

  public TimeWindow getOperatingHours() {
    return operatingHours;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isActive() {
    return active;
  }

  public boolean isOpenAt(LocalTime time) {
    if (time == null) {
      throw new IllegalArgumentException("L'orario da verificare è obbligatorio.");
    }

    return active && operatingHours.contains(time);
  }

  public boolean isInCountry(String countryCode) {
    return location.isInCountry(countryCode);
  }

  public boolean hasCoordinates() {
    return location.hasCoordinates();
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public String formatSingleLine() {
    return code + " - " + type + " - " + location.formatSingleLine();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Facility facility)) return false;
    return active == facility.active
        && code.equals(facility.code)
        && type == facility.type
        && location.equals(facility.location)
        && operatingHours.equals(facility.operatingHours)
        && notes.equals(facility.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, type, location, operatingHours, notes, active);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
