package it.gabriele.truckflow.domain.economics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class FleetAssetPurchaseTest {

  @Test
  void shouldTrackTruckPurchaseAndCalculateDepreciation() {
    FleetAssetPurchase tractor =
        FleetAssetPurchase.vehicle(
            "ASSET-TR-001",
            FleetAssetCategory.TRACTOR_UNIT,
            "SUP-INV-001",
            "TRUCK-001",
            "Trattore stradale 4x2 con retarder",
            LocalDate.of(2026, 1, 15),
            Money.of("120000", "EUR"),
            Money.of("30000", "EUR"),
            72,
            Notes.of("Acquisto mezzo usato garantito"));

    assertTrue(tractor.isAssignedToVehicle());
    assertEquals(Money.of("90000", "EUR"), tractor.calculateDepreciableValue());
    assertEquals(Money.of("1250.00", "EUR"), tractor.calculateMonthlyDepreciation());
    assertEquals(Money.of("3750.00", "EUR"), tractor.calculateDepreciationForMonths(3));
  }

  @Test
  void shouldRejectResidualValueGreaterThanPurchasePrice() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            FleetAssetPurchase.of(
                "ASSET-001",
                FleetAssetCategory.SEMI_TRAILER,
                "SUP-INV-001",
                "TRL-001",
                "Semirimorchio centinato",
                LocalDate.of(2026, 1, 15),
                Money.of("30000", "EUR"),
                Money.of("40000", "EUR"),
                60,
                Notes.empty()));
  }
}
