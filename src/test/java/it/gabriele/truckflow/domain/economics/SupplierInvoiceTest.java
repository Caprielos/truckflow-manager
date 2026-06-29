package it.gabriele.truckflow.domain.economics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class SupplierInvoiceTest {

  @Test
  void shouldSeparateCapitalAssetsFromOperatingExpenses() {
    SupplierInvoice invoice =
        SupplierInvoice.received(
            "SUP-2026-001",
            "SCANIA-DEALER",
            LocalDate.of(2026, 2, 1),
            LocalDate.of(2026, 3, 1),
            List.of(
                PurchaseLine.of(
                    "L1",
                    PurchaseCategory.VEHICLE_PURCHASE,
                    "Trattore stradale",
                    Money.of("120000", "EUR"),
                    Notes.empty()),
                PurchaseLine.of(
                    "L2",
                    PurchaseCategory.INSURANCE_PREMIUM,
                    "Prima rata assicurazione",
                    Money.of("3500", "EUR"),
                    Notes.empty())),
            Notes.empty());

    assertEquals(Money.of("123500", "EUR"), invoice.calculateTotal());
    assertEquals(Money.of("120000", "EUR"), invoice.calculateCapitalAssetTotal());
    assertEquals(Money.of("3500", "EUR"), invoice.calculateOperatingExpenseTotal());
    assertTrue(invoice.containsCapitalAssets());
    assertTrue(invoice.containsOperatingExpenses());
  }

  @Test
  void shouldRejectDuplicatedPurchaseLineCodes() {
    PurchaseLine first =
        PurchaseLine.of(
            "L1", PurchaseCategory.FUEL, "Gasolio", Money.of("500", "EUR"), Notes.empty());
    PurchaseLine duplicated =
        PurchaseLine.of(
            "L1", PurchaseCategory.TOLL, "Pedaggi", Money.of("120", "EUR"), Notes.empty());

    assertThrows(
        IllegalArgumentException.class,
        () ->
            SupplierInvoice.received(
                "SUP-2026-002",
                "SUPPLIER-001",
                LocalDate.of(2026, 2, 1),
                LocalDate.of(2026, 3, 1),
                first,
                duplicated));
  }
}
