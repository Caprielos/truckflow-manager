package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxedSupplierInvoiceTest {

    @Test
    void shouldSeparateNetVatGrossAndAccountingCostOnSupplierInvoice() {
        PurchaseLine tractor = PurchaseLine.taxableNet(
                "L1",
                PurchaseCategory.TRACTOR_UNIT_PURCHASE,
                "Trattore stradale 4x2",
                Money.of("100000", "EUR"),
                VatRate.italianStandard22(),
                Notes.empty()
        );
        PurchaseLine insurance = PurchaseLine.taxed(
                "L2",
                PurchaseCategory.INSURANCE_PREMIUM,
                "Assicurazione annuale non detraibile",
                VatBreakdown.nonDeductibleFromNet(Money.of("5000", "EUR"), VatRate.italianStandard22()),
                Notes.empty()
        );

        SupplierInvoice invoice = SupplierInvoice.received(
                "SUP-TRUCK-2026",
                "DEALER-001",
                LocalDate.of(2026, 1, 10),
                LocalDate.of(2026, 2, 10),
                List.of(tractor, insurance),
                Notes.empty()
        );

        assertEquals(Money.of("105000", "EUR"), invoice.calculateNetTotal());
        assertEquals(Money.of("23100.00", "EUR"), invoice.calculateVatTotal());
        assertEquals(Money.of("128100.00", "EUR"), invoice.calculateTotal());
        assertEquals(Money.of("22000.00", "EUR"), invoice.calculateRecoverableVatTotal());
        assertEquals(Money.of("106100.00", "EUR"), invoice.calculateAccountingCostTotal());
        assertEquals(Money.of("100000.00", "EUR"), invoice.calculateCapitalAssetAccountingCostTotal());
        assertEquals(Money.of("6100.00", "EUR"), invoice.calculateOperatingExpenseAccountingCostTotal());
    }
}
