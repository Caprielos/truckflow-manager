package it.gabriele.truckflow.deadlineservice.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ManagedElementCatalogTest {

  @Test
  void shouldCoverEveryManagedElementCode() {
    Set<ManagedElementCode> catalogCodes = EnumSet.noneOf(ManagedElementCode.class);

    for (ManagedElementDefinition definition : ManagedElementCatalog.all()) {
      catalogCodes.add(definition.code());
    }

    assertThat(catalogCodes).containsExactlyInAnyOrder(ManagedElementCode.values());
  }

  @Test
  void shouldContainAllRequestedVehicleMaintenanceElements() {
    assertTechnicalVehicle(ManagedElementCode.VEHICLE_ENGINE_OIL);
    assertTechnicalVehicle(ManagedElementCode.VEHICLE_FILTERS);
    assertTechnicalVehicle(ManagedElementCode.VEHICLE_BRAKES);
    assertTechnicalVehicle(ManagedElementCode.VEHICLE_COOLANT);
    assertTechnicalVehicle(ManagedElementCode.VEHICLE_ADBLUE_SYSTEM);
    assertTechnicalVehicle(ManagedElementCode.VEHICLE_BELTS);
    assertTechnicalVehicle(ManagedElementCode.VEHICLE_BATTERY);
    assertTechnicalVehicle(ManagedElementCode.VEHICLE_SUSPENSION);
    assertTechnicalVehicle(ManagedElementCode.VEHICLE_LIGHTS);

    ManagedElementDefinition diagnostic =
        ManagedElementCatalog.require(ManagedElementCode.VEHICLE_ENGINE_DIAGNOSTIC);
    assertThat(diagnostic.requiresContinuousMonitoring()).isTrue();
    assertThat(diagnostic.canBlockOperations()).isTrue();
  }

  @Test
  void shouldContainAllRequestedTrailerComponents() {
    assertTechnicalTrailer(ManagedElementCode.TRAILER_BRAKING_SYSTEM);
    assertTechnicalTrailer(ManagedElementCode.TRAILER_ELECTRICAL_SYSTEM);
    assertTechnicalTrailer(ManagedElementCode.TRAILER_REFRIGERATION_UNIT);
    assertTechnicalTrailer(ManagedElementCode.TRAILER_BODY_FLOOR);
    assertTechnicalTrailer(ManagedElementCode.TRAILER_DOORS_LOCKS);
    assertTechnicalTrailer(ManagedElementCode.TRAILER_FIFTH_WHEEL_COUPLING);
    assertTechnicalTrailer(ManagedElementCode.TRAILER_TAIL_LIFT);
  }

  @Test
  void legalDocumentsShouldDependOnConfiguredCountry() {
    ManagedElementDefinition roadworthiness =
        ManagedElementCatalog.require(ManagedElementCode.VEHICLE_ROADWORTHINESS_TEST);
    ManagedElementDefinition cqc = ManagedElementCatalog.require(ManagedElementCode.DRIVER_CQC);

    assertThat(roadworthiness.hasLegalSource()).isTrue();
    assertThat(roadworthiness.dynamicByConfiguredCountry()).isTrue();
    assertThat(roadworthiness.canBlockOperations()).isTrue();
    assertThat(cqc.hasLegalSource()).isTrue();
    assertThat(cqc.dynamicByConfiguredCountry()).isTrue();
    assertThat(cqc.canBlockOperations()).isTrue();
  }

  @Test
  void telematicsElementsShouldRequireContinuousMonitoring() {
    for (ManagedElementDefinition definition :
        ManagedElementCatalog.byCategory(ManagedElementCategory.TELEMATICS)) {
      assertThat(definition.requiresContinuousMonitoring()).isTrue();
      assertThat(definition.hasMonitoringSource()).isTrue();
    }
  }

  @Test
  void deadlineSubjectShouldStayGenericAndIndependentFromMainDomain() {
    DeadlineSubject subject =
        new DeadlineSubject(
            new DeadlineObjectRef("COMPANY-001", "VEHICLE", "VEH-001", "AB123CD"),
            "IT",
            "IVECO",
            "S-WAY",
            Set.of(ManagedElementCode.VEHICLE_ENGINE_OIL),
            Map.of("currentKm", "180000", "lastEngineOilChangeKm", "100000"));

    assertThat(subject.objectRef().objectType()).isEqualTo("VEHICLE");
    assertThat(subject.hasElement(ManagedElementCode.VEHICLE_ENGINE_OIL)).isTrue();
    assertThat(subject.fact("currentKm")).isEqualTo("180000");
  }

  @Test
  void objectReferenceShouldRejectMissingIdentityFields() {
    assertThrows(
        IllegalArgumentException.class, () -> new DeadlineObjectRef("", "VEHICLE", "VEH-001", ""));
    assertThrows(
        IllegalArgumentException.class,
        () -> new DeadlineObjectRef("COMPANY-001", "", "VEH-001", ""));
    assertThrows(
        IllegalArgumentException.class,
        () -> new DeadlineObjectRef("COMPANY-001", "VEHICLE", "", ""));
  }

  private static void assertTechnicalVehicle(ManagedElementCode code) {
    ManagedElementDefinition definition = ManagedElementCatalog.require(code);

    assertThat(definition.category()).isEqualTo(ManagedElementCategory.VEHICLE_MAINTENANCE);
    assertThat(definition.ownerType()).isEqualTo(ManagedElementOwnerType.VEHICLE);
    assertThat(definition.hasTechnicalSource()).isTrue();
    assertThat(definition.dynamicByManufacturerModel()).isTrue();
  }

  private static void assertTechnicalTrailer(ManagedElementCode code) {
    ManagedElementDefinition definition = ManagedElementCatalog.require(code);

    assertThat(definition.category()).isEqualTo(ManagedElementCategory.TRAILER_COMPONENT);
    assertThat(definition.ownerType()).isEqualTo(ManagedElementOwnerType.TRAILER);
    assertThat(definition.hasTechnicalSource()).isTrue();
    assertThat(definition.dynamicByManufacturerModel()).isTrue();
  }
}
