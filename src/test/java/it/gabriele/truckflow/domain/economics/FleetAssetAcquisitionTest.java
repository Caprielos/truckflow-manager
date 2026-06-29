package it.gabriele.truckflow.domain.economics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class FleetAssetAcquisitionTest {

  @Test
  void shouldTrackCompleteTruckTrailerAndEquipmentPurchaseWithVat() {
    FleetAssetAcquisition acquisition =
        FleetAssetAcquisition.of(
            "ACQ-2026-001",
            "DEALER-001",
            "SUP-ACQ-001",
            LocalDate.of(2026, 1, 15),
            List.of(
                FleetAssetCostComponent.taxableNet(
                    "C1",
                    FleetAssetCostComponentType.TRACTOR_UNIT,
                    "Trattore stradale",
                    Money.of("120000", "EUR"),
                    VatRate.italianStandard22(),
                    "TRUCK-001",
                    Notes.empty()),
                FleetAssetCostComponent.taxableNet(
                    "C2",
                    FleetAssetCostComponentType.SEMI_TRAILER,
                    "Semirimorchio centinato",
                    Money.of("40000", "EUR"),
                    VatRate.italianStandard22(),
                    "TRL-001",
                    Notes.empty()),
                FleetAssetCostComponent.taxableNet(
                    "C3",
                    FleetAssetCostComponentType.REFRIGERATED_BODY,
                    "Allestimento isotermico",
                    Money.of("18000", "EUR"),
                    VatRate.italianStandard22(),
                    "TRL-001",
                    Notes.empty()),
                FleetAssetCostComponent.taxableNet(
                    "C4",
                    FleetAssetCostComponentType.TIRE_SET,
                    "Set gomme iniziale",
                    Money.of("3600", "EUR"),
                    VatRate.italianStandard22(),
                    "TRL-001",
                    Notes.empty()),
                FleetAssetCostComponent.taxableNet(
                    "C5",
                    FleetAssetCostComponentType.TELEMATICS_DEVICE,
                    "Dispositivo telematico",
                    Money.of("850", "EUR"),
                    VatRate.italianStandard22(),
                    "TRUCK-001",
                    Notes.empty()),
                FleetAssetCostComponent.of(
                    "C6",
                    FleetAssetCostComponentType.REGISTRATION,
                    "Immatricolazione e pratiche",
                    VatBreakdown.outOfScope(Money.of("1200", "EUR"), "NO_IVA_PRACTICE"),
                    "TRUCK-001",
                    Notes.empty()),
                FleetAssetCostComponent.of(
                    "C7",
                    FleetAssetCostComponentType.INITIAL_INSURANCE,
                    "Prima assicurazione non detraibile",
                    VatBreakdown.nonDeductibleFromNet(
                        Money.of("3000", "EUR"), VatRate.italianStandard22()),
                    "TRUCK-001",
                    Notes.empty())),
            Notes.empty());

    assertEquals(Money.of("186650", "EUR"), acquisition.calculateNetTotal());
    assertEquals(Money.of("40799.00", "EUR"), acquisition.calculateVatTotal());
    assertEquals(Money.of("227449.00", "EUR"), acquisition.calculateGrossTotal());
    assertEquals(Money.of("40139.00", "EUR"), acquisition.calculateRecoverableVatTotal());
    assertEquals(Money.of("187310.00", "EUR"), acquisition.calculateAccountingCostTotal());
    assertEquals(Money.of("160000.00", "EUR"), acquisition.calculateVehicleUnitCost());
    assertEquals(Money.of("18000.00", "EUR"), acquisition.calculateBodyAndEquipmentCost());
    assertEquals(Money.of("3600.00", "EUR"), acquisition.calculateTireCost());
    assertTrue(acquisition.contains(FleetAssetCostComponentType.REFRIGERATED_BODY));
  }
}
