package it.gabriele.truckflow.domain.operation;

/** Stato operativo di una missione di trasporto. */
public enum TransportMissionStatus {
  PLANNED(false),
  DISPATCHED(false),
  IN_PROGRESS(false),
  COMPLETED(true),
  CANCELLED(true);

  private final boolean terminal;

  TransportMissionStatus(boolean terminal) {
    this.terminal = terminal;
  }

  public boolean isTerminal() {
    return terminal;
  }
}
