package it.gabriele.truckflow.domain.maintenance;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDateTime;
import java.util.Objects;

/** Segnalazione guasto o anomalia fatta dall'autista prima/durante/dopo la missione. */
public final class DriverDefectTicket {

  private final String ticketNumber;
  private final String vehicleFleetNumber;
  private final String driverCode;
  private final LocalDateTime reportedAt;
  private final String defectDescription;
  private final boolean vehicleBlocked;
  private final Notes notes;

  private DriverDefectTicket(
      String ticketNumber,
      String vehicleFleetNumber,
      String driverCode,
      LocalDateTime reportedAt,
      String defectDescription,
      boolean vehicleBlocked,
      Notes notes) {
    this.ticketNumber = normalizeCode(ticketNumber, "Il numero ticket è obbligatorio.");
    this.vehicleFleetNumber =
        normalizeCode(vehicleFleetNumber, "Il numero flotta mezzo è obbligatorio.");
    this.driverCode = normalizeCode(driverCode, "Il codice autista è obbligatorio.");
    if (reportedAt == null) {
      throw new IllegalArgumentException("La data segnalazione è obbligatoria.");
    }
    if (defectDescription == null || defectDescription.trim().isEmpty()) {
      throw new IllegalArgumentException("La descrizione guasto è obbligatoria.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note ticket sono obbligatorie.");
    }
    this.reportedAt = reportedAt;
    this.defectDescription = defectDescription.trim();
    this.vehicleBlocked = vehicleBlocked;
    this.notes = notes;
  }

  public static DriverDefectTicket of(
      String ticketNumber,
      String vehicleFleetNumber,
      String driverCode,
      LocalDateTime reportedAt,
      String defectDescription,
      boolean vehicleBlocked,
      Notes notes) {
    return new DriverDefectTicket(
        ticketNumber,
        vehicleFleetNumber,
        driverCode,
        reportedAt,
        defectDescription,
        vehicleBlocked,
        notes);
  }

  private static String normalizeCode(String value, String nullMessage) {
    if (value == null) {
      throw new IllegalArgumentException(nullMessage);
    }
    String normalized = value.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }
    return normalized;
  }

  public String getTicketNumber() {
    return ticketNumber;
  }

  public String getVehicleFleetNumber() {
    return vehicleFleetNumber;
  }

  public String getDriverCode() {
    return driverCode;
  }

  public LocalDateTime getReportedAt() {
    return reportedAt;
  }

  public String getDefectDescription() {
    return defectDescription;
  }

  public boolean isVehicleBlocked() {
    return vehicleBlocked;
  }

  public Notes getNotes() {
    return notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DriverDefectTicket that)) return false;
    return vehicleBlocked == that.vehicleBlocked
        && ticketNumber.equals(that.ticketNumber)
        && vehicleFleetNumber.equals(that.vehicleFleetNumber)
        && driverCode.equals(that.driverCode)
        && reportedAt.equals(that.reportedAt)
        && defectDescription.equals(that.defectDescription)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        ticketNumber,
        vehicleFleetNumber,
        driverCode,
        reportedAt,
        defectDescription,
        vehicleBlocked,
        notes);
  }
}
