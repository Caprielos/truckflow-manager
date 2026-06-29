package it.gabriele.truckflow.domain.warehouse;

import java.time.LocalDateTime;

/** Prenotazione slot per carico/scarico in baia. */
public record DockAppointment(
    String appointmentCode,
    String dockCode,
    String missionCode,
    LocalDateTime plannedStart,
    LocalDateTime plannedEnd,
    LocalDateTime checkInAt,
    DockAppointmentStatus status) {

  public DockAppointment {
    appointmentCode = normalize(appointmentCode, "Il codice appuntamento è obbligatorio.");
    dockCode = normalize(dockCode, "Il codice baia è obbligatorio.");
    missionCode = normalize(missionCode, "Il codice missione è obbligatorio.");
    if (plannedStart == null || plannedEnd == null || !plannedEnd.isAfter(plannedStart)) {
      throw new IllegalArgumentException("La finestra appuntamento non è valida.");
    }
    if (status == null) {
      throw new IllegalArgumentException("Lo stato appuntamento è obbligatorio.");
    }
  }

  public boolean isLateAt(LocalDateTime now) {
    return now != null && checkInAt == null && now.isAfter(plannedStart);
  }

  public boolean blocksDock() {
    return status == DockAppointmentStatus.CONFIRMED
        || status == DockAppointmentStatus.CHECKED_IN
        || status == DockAppointmentStatus.LOADING
        || status == DockAppointmentStatus.UNLOADING;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
