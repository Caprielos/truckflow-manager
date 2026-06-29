package it.gabriele.truckflow.domain.notification;

/** Stato di una notifica. */
public enum NotificationStatus {
  DRAFT(false, false),
  SCHEDULED(false, false),
  SENT(true, true),
  FAILED(true, false),
  CANCELLED(true, false);

  private final boolean terminal;
  private final boolean delivered;

  NotificationStatus(boolean terminal, boolean delivered) {
    this.terminal = terminal;
    this.delivered = delivered;
  }

  public boolean isTerminal() {
    return terminal;
  }

  public boolean isDelivered() {
    return delivered;
  }
}
