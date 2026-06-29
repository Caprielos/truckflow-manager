package it.gabriele.truckflow.domain.warehouse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WarehouseEnterpriseModelTest {

  @Test
  void shouldManageYardDockSlotStockAndCrossDocking() {
    WarehouseLocation location =
        new WarehouseLocation(
            "loc-a-01",
            WarehouseAreaType.CROSS_DOCKING,
            "A",
            "01",
            "L1",
            20,
            false,
            false,
            Set.of("PALLET"));
    StockPosition stock =
        new StockPosition("item-001", "batch-001", "loc-a-01", 10, LocalDateTime.now(), false);
    LoadingDock dock =
        new LoadingDock("dock-01", "depot-01", 18, LoadingDockStatus.AVAILABLE, Set.of("PALLET"));
    DockAppointment appointment =
        new DockAppointment(
            "app-001",
            "dock-01",
            "mission-001",
            LocalDateTime.of(2026, 6, 29, 10, 0),
            LocalDateTime.of(2026, 6, 29, 11, 0),
            null,
            DockAppointmentStatus.CONFIRMED);
    CrossDockFlow flow =
        new CrossDockFlow(
            "flow-001",
            "ship-in-001",
            "ship-out-001",
            LocalDateTime.of(2026, 6, 29, 9, 0),
            LocalDateTime.of(2026, 6, 29, 12, 0),
            8);

    assertTrue(location.canStore("PALLET"));
    assertTrue(stock.isAvailable());
    assertTrue(dock.isUsableFor("PALLET", 16));
    assertTrue(appointment.blocksDock());
    assertTrue(
        WarehouseOperationalRules.requiresDelayAlert(
            appointment, LocalDateTime.of(2026, 6, 29, 10, 30)));
    assertTrue(WarehouseOperationalRules.isEfficientCrossDock(flow, Duration.ofHours(4)));
    assertFalse(WarehouseOperationalRules.isEfficientCrossDock(flow, Duration.ofHours(2)));
  }
}
