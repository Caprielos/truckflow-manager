package it.gabriele.truckflow.domain.warehouse;

/** Stato appuntamento baia. */
public enum DockAppointmentStatus {
  REQUESTED,
  CONFIRMED,
  CHECKED_IN,
  LOADING,
  UNLOADING,
  COMPLETED,
  NO_SHOW,
  CANCELLED
}
