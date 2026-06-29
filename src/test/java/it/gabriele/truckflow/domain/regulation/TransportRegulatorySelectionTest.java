package it.gabriele.truckflow.domain.regulation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class TransportRegulatorySelectionTest {

  @Test
  void shouldLoadItalyAsFullyConfiguredCountryProfile() {
    TransportRegulatorySelection selection =
        TransportRegulatorySelection.startWithCountry(
            "COMPANY-IT", EuropeanCountry.ITALY, LocalDate.of(2026, 1, 1));

    assertTrue(selection.isFullyConfigured());
    assertTrue(RegulatoryConfigurationRules.canOperateWithFullCompliance(selection));
    assertTrue(selection.requires(RegulatoryRequirementCode.ADR_TRANSPORT_DOCUMENT));
    assertTrue(selection.requires(RegulatoryRequirementCode.ATP_CERTIFICATE));
    assertTrue(selection.requires(RegulatoryRequirementCode.TACHOGRAPH_DRIVING_TIME_CONTROLS));
    assertTrue(selection.requires(RegulatoryRequirementCode.ITALIAN_WASTE_IDENTIFICATION_FORM));
    assertTrue(
        selection.requires(RegulatoryRequirementCode.ITALIAN_ENVIRONMENTAL_MANAGER_REGISTRATION));
    assertTrue(selection.requires(RegulatoryRequirementCode.ITALIAN_CUSTOMS_AIDA));
    assertTrue(selection.requires(RegulatoryRequirementCode.ITALIAN_ELECTRONIC_INVOICING_SDI));
    assertTrue(
        selection
            .activeProfile()
            .hasIntegration(RegulatoryIntegrationSystem.ELECTRONIC_INVOICING_SYSTEM));
  }

  @Test
  void shouldSwitchCountryAndRemoveItalyOnlyRequirementsUntilConfigured() {
    TransportRegulatorySelection italy =
        TransportRegulatorySelection.startWithCountry(
            "COMPANY-IT", EuropeanCountry.ITALY, LocalDate.of(2026, 1, 1));

    TransportRegulatorySelection france =
        italy.changeCountry(EuropeanCountry.FRANCE, LocalDate.of(2026, 2, 1));

    assertFalse(france.isFullyConfigured());
    assertTrue(RegulatoryConfigurationRules.shouldBlockBecauseCountryIsNotConfigured(france));
    assertFalse(france.requires(RegulatoryRequirementCode.ITALIAN_WASTE_IDENTIFICATION_FORM));
    assertFalse(france.requires(RegulatoryRequirementCode.ITALIAN_CUSTOMS_AIDA));
    assertFalse(france.requires(RegulatoryRequirementCode.ITALIAN_ELECTRONIC_INVOICING_SDI));
  }
}
