package it.gabriele.truckflow.domain.warehouse;

import java.time.Duration;
import java.time.LocalDateTime;

/** Regole operative di magazzino, piazzale, slot e cross-docking. */
public final class WarehouseOperationalRules {

  private WarehouseOperationalRules() {}

  public static boolean canAllocateStock(WarehouseLocation location, StockPosition stock) {
    if (location == null || stock == null) {
      throw new IllegalArgumentException("Ubicazione e stock sono obbligatori.");
    }
    return stock.isAvailable() && location.canStore(null);
  }

  public static boolean requiresDelayAlert(DockAppointment appointment, LocalDateTime now) {
    if (appointment == null) {
      throw new IllegalArgumentException("L'appuntamento è obbligatorio.");
    }
    return appointment.isLateAt(now);
  }

  public static boolean isEfficientCrossDock(CrossDockFlow flow, Duration maxDwellTime) {
    if (flow == null || maxDwellTime == null) {
      throw new IllegalArgumentException("Flusso e tempo massimo sono obbligatori.");
    }
    return flow.dispatchedAt() != null && !flow.dwellTime().minus(maxDwellTime).isPositive();
  }
}
