package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FleetEconomicLedgerTest {

    @Test
    void shouldDistinguishProfitVatCreditAndCashDebtWhenBuyingFleetAssets() {
        CustomerRevenueInvoice customerInvoice = CustomerRevenueInvoice.of(
                "INV-CLI-001",
                "CUSTOMER-001",
                "SHP-001",
                LocalDate.of(2026, 4, 3),
                LocalDate.of(2026, 5, 3),
                TaxableRevenueLine.baseTransportFee(
                        "R1",
                        "Trasporto espresso Milano Roma",
                        Money.of("5000", "EUR"),
                        VatRate.italianStandard22(),
                        Notes.empty()
                )
        );

        MissionEconomics mission = MissionEconomics.of(
                "MIS-001",
                "SHP-001",
                customerInvoice.toMissionRevenueLines(),
                List.of(
                        MissionCostLine.of("COST-FUEL", MissionCostType.FUEL,
                                "Carburante", Money.of("1500", "EUR"), Notes.empty()),
                        MissionCostLine.of("COST-TOLL", MissionCostType.TOLL,
                                "Pedaggi", Money.of("800", "EUR"), Notes.empty())
                ),
                Notes.empty()
        );

        FleetAssetAcquisition tractor = FleetAssetAcquisition.of(
                "ACQ-TRACTOR-001",
                "DEALER-001",
                "SUP-TRACTOR-001",
                LocalDate.of(2026, 4, 1),
                FleetAssetCostComponent.taxableNet(
                        "TR1",
                        FleetAssetCostComponentType.TRACTOR_UNIT,
                        "Trattore stradale nuovo",
                        Money.of("100000", "EUR"),
                        VatRate.italianStandard22(),
                        "TRUCK-001",
                        Notes.empty()
                )
        );

        RecurringExpense insurance = RecurringExpense.noVat(
                "INS-APRIL",
                RecurringExpenseCategory.INSURANCE,
                "Quota assicurazione flotta aprile",
                DateRange.of(LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 30)),
                Money.of("1500", "EUR"),
                "TRUCK-001",
                Notes.empty()
        );

        FleetEconomicLedger ledger = FleetEconomicLedger.of(
                "LEDGER-2026-04",
                DateRange.of(LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 30)),
                Money.of("30000", "EUR"),
                List.of(customerInvoice),
                List.of(mission),
                List.of(),
                List.of(tractor),
                List.of(insurance),
                List.of(),
                Notes.empty()
        );

        assertEquals(Money.of("5000", "EUR"), ledger.calculateCustomerRevenueNet());
        assertEquals(Money.of("6100.00", "EUR"), ledger.calculateCustomerGrossInvoiced());
        assertEquals(Money.of("1100.00", "EUR"), ledger.calculateSalesVatCollected());
        assertEquals(Money.of("22000.00", "EUR"), ledger.calculateRecoverableVatTotal());
        assertTrue(ledger.hasVatCredit());
        assertFalse(ledger.hasVatDebt());
        assertEquals(Money.of("20900.00", "EUR"), ledger.calculateVatPosition().absoluteMoney());

        assertEquals(ProfitabilityStatus.PROFIT, ledger.calculateAccountingProfitability().getStatus());
        assertEquals(Money.of("1200", "EUR"), ledger.calculateAccountingProfitability().getNetResult().absoluteMoney());

        assertTrue(ledger.isCashNegative());
        assertEquals(Money.of("89700.00", "EUR"), ledger.calculateDebtAmount());
        assertTrue(EconomicsRules.ledgerHasCashDebt(ledger));
        assertTrue(EconomicsRules.ledgerHasVatCredit(ledger));
    }
}
