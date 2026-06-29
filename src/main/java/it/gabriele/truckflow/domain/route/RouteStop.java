package it.gabriele.truckflow.domain.route;

import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import java.util.Objects;

/**
 * Rappresenta una fermata pianificata dentro una tratta. Esempio: partenza, ritiro, consegna, pausa
 * o arrivo.
 */
public final class RouteStop {

  private final int sequenceNumber;
  private final RouteStopType type;
  private final Facility facility;
  private final TimeWindow plannedTimeWindow;
  private final Notes notes;

  private RouteStop(
      int sequenceNumber,
      RouteStopType type,
      Facility facility,
      TimeWindow plannedTimeWindow,
      Notes notes) {
    if (sequenceNumber <= 0) {
      throw new IllegalArgumentException("Il numero di sequenza deve essere maggiore di zero.");
    }

    if (type == null) {
      throw new IllegalArgumentException("Il tipo di fermata è obbligatorio.");
    }

    if (facility == null) {
      throw new IllegalArgumentException("La struttura della fermata è obbligatoria.");
    }

    if (plannedTimeWindow == null) {
      throw new IllegalArgumentException("La finestra oraria pianificata è obbligatoria.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note della fermata sono obbligatorie.");
    }

    this.sequenceNumber = sequenceNumber;
    this.type = type;
    this.facility = facility;
    this.plannedTimeWindow = plannedTimeWindow;
    this.notes = notes;
  }

  public static RouteStop of(
      int sequenceNumber,
      RouteStopType type,
      Facility facility,
      TimeWindow plannedTimeWindow,
      Notes notes) {
    return new RouteStop(sequenceNumber, type, facility, plannedTimeWindow, notes);
  }

  public int getSequenceNumber() {
    return sequenceNumber;
  }

  public RouteStopType getType() {
    return type;
  }

  public Facility getFacility() {
    return facility;
  }

  public TimeWindow getPlannedTimeWindow() {
    return plannedTimeWindow;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isStart() {
    return type == RouteStopType.START;
  }

  public boolean isEnd() {
    return type == RouteStopType.END;
  }

  public boolean isPickup() {
    return type == RouteStopType.PICKUP;
  }

  public boolean isDelivery() {
    return type == RouteStopType.DELIVERY;
  }

  public boolean isCargoOperation() {
    return type.isCargoOperation();
  }

  public boolean isAtSameFacility(RouteStop other) {
    if (other == null) {
      throw new IllegalArgumentException("La fermata da confrontare è obbligatoria.");
    }

    return this.facility.equals(other.facility);
  }

  public boolean isBefore(RouteStop other) {
    if (other == null) {
      throw new IllegalArgumentException("La fermata da confrontare è obbligatoria.");
    }

    return this.sequenceNumber < other.sequenceNumber;
  }

  public String formatSingleLine() {
    return sequenceNumber + " - " + type + " - " + facility.getCode() + " - " + plannedTimeWindow;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RouteStop routeStop)) return false;
    return sequenceNumber == routeStop.sequenceNumber
        && type == routeStop.type
        && facility.equals(routeStop.facility)
        && plannedTimeWindow.equals(routeStop.plannedTimeWindow)
        && notes.equals(routeStop.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sequenceNumber, type, facility, plannedTimeWindow, notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
