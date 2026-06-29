package it.gabriele.truckflow.domain.inventory;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class InventoryManagementTest {

  @Test
  void shouldCalculateWarehouseStockAndReorderSignal() {
    WarehouseLocation location = WarehouseLocation.of("DEPOT-MI", "A", "S01", "B10");
    InventoryItem brakePads =
        InventoryItem.of(
            "PAD-001",
            InventoryItemType.BRAKE_COMPONENT,
            "Pastiglie freno asse trattore",
            "pcs",
            Money.of("85.00", "EUR"),
            4,
            Notes.empty());

    InventoryStockMovement in =
        InventoryStockMovement.of(
            "MOV-001",
            "PAD-001",
            location,
            StockMovementType.PURCHASE_IN,
            6,
            Money.of("85.00", "EUR"),
            LocalDateTime.of(2026, 6, 1, 9, 0),
            "INV-001",
            Notes.empty());
    InventoryStockMovement used =
        InventoryStockMovement.of(
            "MOV-002",
            "PAD-001",
            location,
            StockMovementType.CONSUMPTION_MAINTENANCE,
            3,
            Money.of("85.00", "EUR"),
            LocalDateTime.of(2026, 6, 5, 11, 0),
            "WO-001",
            Notes.empty());

    InventoryBalance balance =
        InventoryBalance.fromMovements(brakePads, location, List.of(in, used));

    assertEquals(3.0, balance.getAvailableQuantity());
    assertTrue(InventoryRules.shouldReorder(balance));
    assertTrue(InventoryRules.safetyCriticalItemShouldHaveStock(balance));
    assertFalse(balance.canReserve(4));
    assertEquals(Money.of("255.000", "EUR"), balance.calculateStockValue());
  }
}
