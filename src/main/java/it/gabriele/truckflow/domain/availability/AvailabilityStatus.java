package it.gabriele.truckflow.domain.availability;

/** Stato di disponibilità di una risorsa. */
public enum AvailabilityStatus {
  AVAILABLE(true, false),
  RESERVED(false, true),
  ASSIGNED(false, true),
  UNAVAILABLE(false, true),
  MAINTENANCE(false, true),
  ON_LEAVE(false, true);

  private final boolean bookable;
  private final boolean blocking;

  AvailabilityStatus(boolean bookable, boolean blocking) {
    this.bookable = bookable;
    this.blocking = blocking;
  }

  public boolean isBookable() {
    return bookable;
  }

  public boolean isBlocking() {
    return blocking;
  }
}
