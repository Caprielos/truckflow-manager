package it.gabriele.truckflow.domain.contract;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Percentage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerContractModelTest {

    @Test
    void shouldModelCustomerContractRateCardAndRequiredSurcharges() {
        ContractRateCard rateCard = ContractRateCard.of(
                "RC-2026",
                "Listino Nord Italia",
                List.of(
                        TariffRule.amount("BASE", TariffRuleType.BASE_TRANSPORT_FEE, "Trasporto base",
                                ChargeUnit.PER_SHIPMENT, Money.of("950.00", "EUR"), true, Notes.empty()),
                        TariffRule.percentage("FUEL", TariffRuleType.FUEL_SURCHARGE, "Adeguamento gasolio",
                                Percentage.of("8.5"), false, Notes.empty()),
                        TariffRule.amount("ADR", TariffRuleType.ADR_SURCHARGE, "Supplemento ADR",
                                ChargeUnit.FIXED_AMOUNT, Money.of("120.00", "EUR"), false, Notes.empty())
                ),
                Notes.empty()
        );

        CustomerContract contract = CustomerContract.active(
                "CTR-ACME-2026",
                "ACME",
                DateRange.of("2026-01-01", "2026-12-31"),
                rateCard,
                Notes.empty()
        );

        assertTrue(CustomerContractRules.canPriceShipmentOn(contract, java.time.LocalDate.of(2026, 6, 15)));
        assertFalse(CustomerContractRules.requiresManualPricingForAdr(contract));
        assertTrue(CustomerContractRules.requiresManualPricingForTemperatureControlledTransport(contract));
        assertTrue(rateCard.findRule(TariffRuleType.FUEL_SURCHARGE).orElseThrow().getUnit().isPercentageBased());
    }
}
