package it.gabriele.truckflow.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.cargo.CargoCode;
import it.gabriele.truckflow.domain.cargo.exceptions.InvalidCargoException;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdiction;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdictionScope;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.CountryCode;
import it.gabriele.truckflow.domain.compliance.JurisdictionRegion;
import it.gabriele.truckflow.domain.compliance.exceptions.InvalidComplianceRequirementException;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.exceptions.InvalidDocumentException;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.exceptions.InvalidLocationException;
import it.gabriele.truckflow.domain.operational.common.OperationalScope;
import it.gabriele.truckflow.domain.operational.common.OperationalScopeCode;
import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;
import it.gabriele.truckflow.domain.shipments.core.ShipmentCode;
import it.gabriele.truckflow.domain.shipments.exceptions.InvalidShipmentException;
import it.gabriele.truckflow.domain.triptemplates.TripTemplateCode;
import it.gabriele.truckflow.domain.triptemplates.exceptions.InvalidTripTemplateException;
import it.gabriele.truckflow.domain.users.LanguageCode;
import it.gabriele.truckflow.domain.users.UserPreferences;
import it.gabriele.truckflow.domain.users.UserTheme;
import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;
import it.gabriele.truckflow.domain.vehicles.exceptions.InvalidVehicleException;
import it.gabriele.truckflow.domain.vehicles.unit.FleetCode;
import it.gabriele.truckflow.domain.vehicles.unit.LicensePlate;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleIdentificationNumber;
import org.junit.jupiter.api.Test;

class DomainValueObjectContractTest {

  @Test
  void businessCodesNormalizeToUppercaseAndKeepDomainSpecificMeaning() {
    assertEquals("CGO-001", CargoCode.of(" cgo-001 ").value());
    assertEquals("SHP-001", ShipmentCode.of(" shp-001 ").value());
    assertEquals("DEP-MIL-001", LocationCode.of(" dep-mil-001 ").value());
    assertEquals("LINEHAUL-001", TripTemplateCode.of(" linehaul-001 ").value());
    assertEquals("CMR-SHP-001", DocumentCode.of(" cmr-shp-001 ").value());
    assertEquals("CMP-ADR-001", ComplianceRequirementCode.of(" cmp-adr-001 ").value());
    assertEquals("TRC-001", FleetCode.of(" trc-001 ").value());
    assertEquals("NORTH_IT", OperationalScopeCode.of(" north_it ").value());
  }

  @Test
  void invalidBusinessCodesRaiseTheirOwnDomainException() {
    assertThrows(InvalidCargoException.class, () -> CargoCode.of("###"));
    assertThrows(InvalidShipmentException.class, () -> ShipmentCode.of("###"));
    assertThrows(InvalidLocationException.class, () -> LocationCode.of("###"));
    assertThrows(InvalidTripTemplateException.class, () -> TripTemplateCode.of("###"));
    assertThrows(InvalidDocumentException.class, () -> DocumentCode.of("###"));
    assertThrows(DomainValidationException.class, () -> OperationalScopeCode.of("north italy"));
  }

  @Test
  void vehicleIdentifiersAreValidatedAndNormalizedByDedicatedValueObjects() {
    assertEquals("AB123CD", LicensePlate.of(" ab 123 cd ").value());
    assertEquals("VIN-001", VehicleIdentificationNumber.of(" vin-001 ").value());

    assertThrows(InvalidVehicleException.class, () -> LicensePlate.of(" "));
    assertThrows(InvalidVehicleException.class, () -> LicensePlate.of("A"));
    assertThrows(InvalidVehicleException.class, () -> VehicleIdentificationNumber.of("VIN#001"));
  }

  @Test
  void userPreferencesUseLanguageCodeAndThemeInsteadOfFreeStrings() {
    var preferences = new UserPreferences(" it ", " system ", false);

    assertEquals(LanguageCode.of("IT"), preferences.language());
    assertEquals(UserTheme.SYSTEM, preferences.theme());
    assertEquals(LanguageCode.DEFAULT, new UserPreferences(" ", "dark", true).language());
    assertEquals(UserTheme.DEFAULT, new UserPreferences("EN", " ", true).theme());

    assertThrows(InvalidUserException.class, () -> LanguageCode.of("ITA"));
    assertThrows(InvalidUserException.class, () -> UserTheme.from("blue"));
  }

  @Test
  void complianceJurisdictionSeparatesScopeRegionAndCountryWithoutUiDefaults() {
    var italian = ComplianceJurisdiction.italy();
    assertEquals(CountryCode.of("IT"), italian.country().orElseThrow());
    assertTrue(italian.region().isEmpty());
    assertEquals(ComplianceJurisdictionScope.NATIONAL, italian.scope());

    var european = ComplianceJurisdiction.europeanUnion();
    assertTrue(european.country().isEmpty());
    assertEquals(JurisdictionRegion.of("EU"), european.region().orElseThrow());
    assertEquals(ComplianceJurisdictionScope.EUROPEAN_UNION, european.scope());

    var regional = new ComplianceJurisdiction(" it ", " lombardy ", " regional ", "Regional rule");
    assertEquals(CountryCode.of("IT"), regional.country().orElseThrow());
    assertEquals(JurisdictionRegion.of("LOMBARDY"), regional.region().orElseThrow());
    assertEquals(ComplianceJurisdictionScope.REGIONAL, regional.scope());
  }

  @Test
  void complianceJurisdictionRejectsMalformedCountryRegionAndScope() {
    assertThrows(InvalidComplianceRequirementException.class, () -> CountryCode.of("ITA"));
    assertThrows(InvalidComplianceRequirementException.class, () -> JurisdictionRegion.of("EU@"));
    assertThrows(
        InvalidComplianceRequirementException.class,
        () -> ComplianceJurisdictionScope.from("planetary"));
  }

  @Test
  void operationalScopeUsesADedicatedScopeCodeButKeepsAreaFlexible() {
    var scope =
        new OperationalScope(" north_it ", "North Italy", "Domestic planning", "Transport area");

    assertEquals(OperationalScopeCode.of("NORTH_IT"), scope.code());
    assertEquals("North Italy", scope.name());
    assertEquals("Transport area", scope.area());
  }
}
