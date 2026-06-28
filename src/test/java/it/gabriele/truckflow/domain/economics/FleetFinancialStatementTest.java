package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FleetFinancialStatementTest {

    @Test
    void shouldShowCashDebtWhenCompanyBuysVehicleAndRevenueIsNotEnoughYet() {
        MissionEconomics mission = MissionEconomics.of(
                "MIS-001",
                "SHP-001",
                List.of(MissionRevenueLine.baseTransportFee("REV-BASE", "Trasporto cliente", Money.of("1500", "EUR"), Notes.empty())),
                List.of(
                        MissionCostLine.of("COST-FUEL", MissionCostType.FUEL,
                                "Carburante", Money.of("400", "EUR"), Notes.empty()),
                        MissionCostLine.of("COST-TOLL", MissionCostType.TOLL,
                                "Pedaggi", Money.of("180", "EUR"), Notes.empty())
                ),
                Notes.empty()
        );

        SupplierInvoice maintenanceInvoice = SupplierInvoice.received(
                "SUP-MAN-001",
                "WORKSHOP-001",
                LocalDate.of(2026, 4, 2),
                LocalDate.of(2026, 4, 30),
                PurchaseLine.of("L1", PurchaseCategory.MAINTENANCE_LABOR,
                        "Tagliando e controllo freni", Money.of("600", "EUR"), Notes.empty())
        );

        FleetAssetPurchase tractor = FleetAssetPurchase.vehicle(
                "ASSET-TR-001",
                FleetAssetCategory.TRACTOR_UNIT,
                "SUP-TRUCK-001",
                "TRUCK-001",
                "Trattore stradale",
                LocalDate.of(2026, 4, 1),
                Money.of("100000", "EUR"),
                Money.of("25000", "EUR"),
                60,
                Notes.empty()
        );

        FleetFinancialStatement statement = FleetFinancialStatement.of(
                "FIN-2026-04",
                DateRange.of(LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 30)),
                List.of(mission),
                List.of(maintenanceInvoice),
                List.of(tractor),
                Notes.empty()
        );

        assertEquals(Money.of("1500", "EUR"), statement.calculateTotalRevenue());
        assertEquals(Money.of("580", "EUR"), statement.calculateMissionCosts());
        assertEquals(Money.of("600", "EUR"), statement.calculateSupplierInvoiceTotal());
        assertEquals(Money.of("100000", "EUR"), statement.calculateAssetInvestmentTotal());
        assertTrue(statement.isCashNegative());
        assertEquals(Money.of("99680", "EUR"), statement.calculateDebtAmount());
        assertTrue(EconomicsRules.isCompanyCashNegative(statement));
    }
}
