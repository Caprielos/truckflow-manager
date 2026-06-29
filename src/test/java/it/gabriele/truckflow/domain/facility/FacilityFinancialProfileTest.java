package it.gabriele.truckflow.domain.facility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.List;
import org.junit.jupiter.api.Test;

class FacilityFinancialProfileTest {

  @Test
  void shouldCalculateOwnedDepotCostsWithoutRent() {
    FacilityFinancialProfile profile =
        FacilityFinancialProfile.owned(
            "DEPOT-MIL-01",
            Money.of("250000", "EUR"),
            List.of(
                FacilityCostLine.yearly(
                    "TAX-2026",
                    FacilityCostType.PROPERTY_TAX,
                    "IMU e tasse locali",
                    Money.of("3600", "EUR"),
                    Notes.empty()),
                FacilityCostLine.monthly(
                    "UTIL-001",
                    FacilityCostType.UTILITIES,
                    "Utenze deposito",
                    Money.of("450", "EUR"),
                    Notes.empty()),
                FacilityCostLine.monthly(
                    "SEC-001",
                    FacilityCostType.SECURITY,
                    "Vigilanza e videosorveglianza",
                    Money.of("300", "EUR"),
                    Notes.empty())),
            Notes.of("Deposito di proprietà aziendale"));

    assertTrue(profile.isOwned());
    assertFalse(profile.hasRecurringOccupancyPayment());
    assertEquals(Money.of("1050.00", "EUR"), profile.calculateMonthlyRecurringCost());
    assertEquals(Money.of("12600.00", "EUR"), profile.calculateAnnualRecurringCost());
    assertEquals(Money.of("250000", "EUR"), profile.calculateOneTimeCashOut());
  }

  @Test
  void shouldCalculateRentedYardCostsWithRentAndDeposit() {
    FacilityFinancialProfile profile =
        FacilityFinancialProfile.rented(
            "YARD-ROM-01",
            Money.of("1800", "EUR"),
            Money.of("3600", "EUR"),
            List.of(
                FacilityCostLine.monthly(
                    "ELEC-001",
                    FacilityCostType.ELECTRICITY,
                    "Corrente piazzale e cancello",
                    Money.of("220", "EUR"),
                    Notes.empty()),
                FacilityCostLine.yearly(
                    "INS-001",
                    FacilityCostType.INSURANCE,
                    "Assicurazione area",
                    Money.of("1200", "EUR"),
                    Notes.empty()),
                FacilityCostLine.oneTime(
                    "DEP-ACCESS",
                    FacilityCostType.GATE_ACCESS_SYSTEM,
                    "Installazione badge accesso",
                    Money.of("500", "EUR"),
                    Notes.empty())),
            Notes.empty());

    assertFalse(profile.isOwned());
    assertTrue(profile.hasRecurringOccupancyPayment());
    assertEquals(Money.of("2120.00", "EUR"), profile.calculateMonthlyRecurringCost());
    assertEquals(Money.of("25440.00", "EUR"), profile.calculateAnnualRecurringCost());
    assertEquals(Money.of("4100", "EUR"), profile.calculateOneTimeCashOut());
  }

  @Test
  void shouldRejectOwnedFacilityWithMissingPurchasePrice() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FacilityFinancialProfile.owned("DEPOT-MIL-01", null, List.of(), Notes.empty()));
  }
}
