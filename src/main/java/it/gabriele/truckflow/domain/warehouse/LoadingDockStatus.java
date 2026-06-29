package it.gabriele.truckflow.domain.warehouse;

/** Stato operativo di una baia di carico/scarico. */
public enum LoadingDockStatus {
  AVAILABLE,
  BOOKED,
  OCCUPIED,
  CLOSED_FOR_MAINTENANCE,
  BLOCKED_FOR_COMPLIANCE
}
