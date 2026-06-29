package it.gabriele.truckflow.domain.deadlinepolicy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ConfigurableTechnicalDeadlineRuleBookTest {

  @Test
  void shouldExposeCompleteTruckAndTrailerTechnicalElementsForConfiguredModel() {
    Set<ManagedDeadlineElementType> configuredElements =
        ManufacturerTechnicalDeadlineCatalog.rulesFor("Iveco", "S-Way").stream()
            .map(DeadlinePolicyRule::getElementType)
            .collect(Collectors.toSet());

    Set<ManagedDeadlineElementType> requiredTruckElements =
        Set.of(
            ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
            ManagedDeadlineElementType.VEHICLE_AIR_FILTER,
            ManagedDeadlineElementType.VEHICLE_OIL_FILTER,
            ManagedDeadlineElementType.VEHICLE_FUEL_FILTER,
            ManagedDeadlineElementType.VEHICLE_BRAKE_PADS,
            ManagedDeadlineElementType.VEHICLE_BRAKE_DISCS,
            ManagedDeadlineElementType.VEHICLE_COOLANT,
            ManagedDeadlineElementType.VEHICLE_ADBLUE_SYSTEM,
            ManagedDeadlineElementType.VEHICLE_BELTS,
            ManagedDeadlineElementType.VEHICLE_BATTERY,
            ManagedDeadlineElementType.VEHICLE_SUSPENSION,
            ManagedDeadlineElementType.VEHICLE_LIGHTS,
            ManagedDeadlineElementType.VEHICLE_ENGINE_DIAGNOSTIC);

    Set<ManagedDeadlineElementType> requiredTrailerElements =
        Set.of(
            ManagedDeadlineElementType.TRAILER_BRAKING_SYSTEM,
            ManagedDeadlineElementType.TRAILER_ELECTRICAL_SYSTEM,
            ManagedDeadlineElementType.TRAILER_REFRIGERATION_UNIT,
            ManagedDeadlineElementType.TRAILER_BODY_FLOOR,
            ManagedDeadlineElementType.TRAILER_DOORS_LOCKS,
            ManagedDeadlineElementType.TRAILER_FIFTH_WHEEL_COUPLING,
            ManagedDeadlineElementType.TRAILER_LANDING_GEAR,
            ManagedDeadlineElementType.TRAILER_TAIL_LIFT);

    assertTrue(configuredElements.containsAll(requiredTruckElements));
    assertTrue(configuredElements.containsAll(requiredTrailerElements));
  }

  @Test
  void shouldUseSpecificModelRuleInsteadOfGenericRuleForSameElement() {
    var engineOilRules =
        ManufacturerTechnicalDeadlineCatalog.rulesFor("Iveco", "S-Way").stream()
            .filter(rule -> rule.getElementType() == ManagedDeadlineElementType.VEHICLE_ENGINE_OIL)
            .toList();

    assertEquals(1, engineOilRules.size());
    assertEquals("IVECO_SWAY_ENGINE_OIL", engineOilRules.getFirst().getRuleCode());
  }

  @Test
  void shouldCalculateTrailerRefrigerationRuleFromSameConfigurableRuleBook() {
    var plan =
        DeadlinePolicyRules.combine(
            "TRL-001",
            ManagedDeadlineElementType.TRAILER_REFRIGERATION_UNIT,
            java.util.List.of(),
            ManufacturerTechnicalDeadlineCatalog.rulesFor("Schmitz", "S.KO Cool"),
            DeadlineUsageSnapshot.calendarOnly(
                    java.time.LocalDate.of(2026, 6, 29), java.time.LocalDate.of(2026, 1, 1))
                .withRefrigerationHours(1000, 1900));

    assertTrue(plan.hasTechnicalRules());
    assertEquals(
        ManagedDeadlineElementType.TRAILER_REFRIGERATION_UNIT,
        plan.nextEffectiveDeadline().orElseThrow().elementType());
  }
}
