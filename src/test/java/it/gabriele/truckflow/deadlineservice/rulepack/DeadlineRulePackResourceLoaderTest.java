package it.gabriele.truckflow.deadlineservice.rulepack;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineRuleSourceType;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCatalog;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import org.junit.jupiter.api.Test;

class DeadlineRulePackResourceLoaderTest {

  @Test
  void shouldLoadDefaultRulePackFromSingleConfigurationFile() {
    DeadlineRulePack rulePack = DeadlineRulePackResourceLoader.loadDefault();

    assertEquals("truckflow-default-deadline-rule-pack", rulePack.id());
    assertEquals("2026.1", rulePack.version());
    assertEquals(DeadlineRulePackStatus.DRAFT, rulePack.status());
    assertEquals("IT", rulePack.defaultCountry());
    assertEquals("DEFAULT", rulePack.tenantId());
    assertFalse(rulePack.rules().isEmpty());
  }

  @Test
  void shouldCoverEveryManagedElementWithAtLeastOneConfigurableSlot() {
    DeadlineRulePack rulePack = DeadlineRulePackResourceLoader.loadDefault();

    assertDoesNotThrow(() -> DeadlineRulePackCoverageValidator.requireFullCoverage(rulePack));
    assertEquals(ManagedElementCatalog.all().size(), rulePack.rules().size());
  }

  @Test
  void everyInitialRuleShouldBeAnEmptySlotFillableFromUi() {
    DeadlineRulePack rulePack = DeadlineRulePackResourceLoader.loadDefault();

    assertEquals(rulePack.rules().size(), rulePack.emptySlots().size());
    assertTrue(rulePack.rules().stream().allMatch(DeadlineRulePackRule::fillableFromUi));
  }

  @Test
  void shouldKeepTechnicalVehicleAndTrailerSlotsDrivenByManufacturerRules() {
    DeadlineRulePack rulePack = DeadlineRulePackResourceLoader.loadDefault();

    assertRuleHasSource(
        rulePack,
        ManagedElementCode.VEHICLE_ENGINE_OIL,
        DeadlineRuleSourceType.MANUFACTURER_RULEBOOK);
    assertRuleHasSource(
        rulePack,
        ManagedElementCode.TRAILER_REFRIGERATION_UNIT,
        DeadlineRuleSourceType.MANUFACTURER_RULEBOOK);
  }

  @Test
  void shouldKeepLegalDocumentsDrivenByEuropeanAndNationalLaw() {
    DeadlineRulePack rulePack = DeadlineRulePackResourceLoader.loadDefault();

    assertRuleHasSource(rulePack, ManagedElementCode.DRIVER_LICENSE, DeadlineRuleSourceType.EU_LAW);
    assertRuleHasSource(
        rulePack, ManagedElementCode.DRIVER_LICENSE, DeadlineRuleSourceType.NATIONAL_LAW);
    assertRuleHasSource(
        rulePack, ManagedElementCode.VEHICLE_ROADWORTHINESS_TEST, DeadlineRuleSourceType.EU_LAW);
  }

  @Test
  void shouldKeepTelematicsSlotsDrivenByContinuousMonitoringEvents() {
    DeadlineRulePack rulePack = DeadlineRulePackResourceLoader.loadDefault();

    assertRuleHasSource(
        rulePack,
        ManagedElementCode.TELEMATICS_DTC_ENGINE_ERROR,
        DeadlineRuleSourceType.TELEMATICS_EVENT);
    assertRuleHasSource(
        rulePack,
        ManagedElementCode.TELEMATICS_UNAUTHORIZED_OPENING,
        DeadlineRuleSourceType.TELEMATICS_EVENT);
  }

  private static void assertRuleHasSource(
      DeadlineRulePack rulePack, ManagedElementCode code, DeadlineRuleSourceType sourceType) {
    assertTrue(
        rulePack.rulesForElement(code).stream()
            .anyMatch(rule -> rule.sourceTypes().contains(sourceType)),
        () -> "Nessuno slot per " + code + " contiene la fonte " + sourceType);
  }
}
