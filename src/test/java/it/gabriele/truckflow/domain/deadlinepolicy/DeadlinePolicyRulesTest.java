package it.gabriele.truckflow.domain.deadlinepolicy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.regulation.EuropeanCountry;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DeadlinePolicyRulesTest {

  @Test
  void shouldLoadDifferentLegalRulesWhenCountryChanges() {
    var italyRules = CountryLegalDeadlineCatalog.rulesFor(EuropeanCountry.ITALY);
    var germanyRules = CountryLegalDeadlineCatalog.rulesFor(EuropeanCountry.GERMANY);

    assertTrue(
        italyRules.stream().anyMatch(rule -> rule.getRuleCode().equals("IT_VEHICLE_INSPECTION")));
    assertFalse(
        germanyRules.stream().anyMatch(rule -> rule.getRuleCode().equals("IT_VEHICLE_INSPECTION")));
    assertTrue(
        germanyRules.stream()
            .anyMatch(rule -> rule.getRuleCode().equals("EU_TACHOGRAPH_CALIBRATION")));
  }

  @Test
  void shouldLoadDifferentTechnicalRulesWhenVehicleModelChanges() {
    var ivecoRules = ManufacturerTechnicalDeadlineCatalog.rulesFor("Iveco", "S-Way");
    var scaniaRules = ManufacturerTechnicalDeadlineCatalog.rulesFor("Scania", "R-Series");

    var ivecoOilRule =
        ivecoRules.stream()
            .filter(rule -> rule.getElementType() == ManagedDeadlineElementType.VEHICLE_ENGINE_OIL)
            .findFirst()
            .orElseThrow();
    var scaniaOilRule =
        scaniaRules.stream()
            .filter(rule -> rule.getElementType() == ManagedDeadlineElementType.VEHICLE_ENGINE_OIL)
            .findFirst()
            .orElseThrow();

    assertEquals(90000, ivecoOilRule.getInterval().odometerKilometers());
    assertEquals(120000, scaniaOilRule.getInterval().odometerKilometers());
  }

  @Test
  void shouldCombineLegalAndTechnicalDeadlinesAndPickEffectiveOne() {
    var snapshot =
        DeadlineUsageSnapshot.vehicleUsage(
            LocalDate.of(2026, 6, 29), LocalDate.of(2025, 6, 1), 100000, 195000, 4000, 4700);

    CombinedDeadlinePlan plan =
        DeadlinePolicyRules.combine(
            "TRUCK-001",
            ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
            CountryLegalDeadlineCatalog.rulesFor(EuropeanCountry.ITALY),
            ManufacturerTechnicalDeadlineCatalog.rulesFor("Iveco", "S-Way"),
            snapshot);

    assertFalse(plan.hasLegalRules());
    assertTrue(plan.hasTechnicalRules());
    assertTrue(plan.nextEffectiveDeadline().orElseThrow().alreadyDue());
    assertEquals(
        DeadlinePolicySource.TECHNICAL_MANUFACTURER,
        plan.nextEffectiveDeadline().orElseThrow().source());
  }

  @Test
  void shouldCalculateLegalDeadlineFromSelectedCountry() {
    var snapshot =
        DeadlineUsageSnapshot.calendarOnly(LocalDate.of(2026, 6, 29), LocalDate.of(2025, 7, 15));

    CombinedDeadlinePlan plan =
        DeadlinePolicyRules.combine(
            "DRV-001",
            ManagedDeadlineElementType.DRIVER_MEDICAL_CHECK,
            CountryLegalDeadlineCatalog.rulesFor(EuropeanCountry.ITALY),
            ManufacturerTechnicalDeadlineCatalog.rulesFor("Iveco", "S-Way"),
            snapshot);

    assertTrue(plan.hasLegalRules());
    assertFalse(plan.hasTechnicalRules());
    assertEquals(LocalDate.of(2027, 7, 15), plan.nextEffectiveDeadline().orElseThrow().dueDate());
  }
}
