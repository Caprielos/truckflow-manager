package it.gabriele.truckflow.domain.cargo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.cargo.exceptions.InvalidCargoException;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CargoDomainTest {

  @Test
  void createsGeneralPalletizedCargo() {
    var cargo = generalPalletizedCargo();

    assertTrue(cargo.isActive());
    assertEquals(CargoCode.of("CGO-001"), cargo.code());
    assertEquals(CargoType.GENERAL_GOODS, cargo.type());
    assertTrue(cargo.hasCategory(CargoCategory.DRY));
    assertTrue(cargo.hasCategory(CargoCategory.PALLETIZED));
    assertFalse(cargo.isTemperatureControlled());
  }

  @Test
  void createsRefrigeratedFoodCargoWithTransportRequirements() {
    var cargo = refrigeratedFoodCargo();

    assertTrue(cargo.isTemperatureControlled());
    assertTrue(cargo.regulatory().atpRequired());
    assertTrue(cargo.regulatory().foodGradeRequired());
    assertTrue(cargo.requires(CargoTransportRequirement.REFRIGERATED_VEHICLE_REQUIRED));
    assertTrue(cargo.requires(CargoTransportRequirement.TEMPERATURE_CONTROL_REQUIRED));
    assertTrue(cargo.requires(CargoTransportRequirement.ATP_CERTIFICATION_REQUIRED));
  }

  @Test
  void createsAdrCargoWithoutImportingVehicleConcepts() {
    var cargo = adrChemicalCargo();

    assertTrue(cargo.isHazardous());
    assertTrue(cargo.hasCategory(CargoCategory.ADR));
    assertEquals("3", cargo.hazard().adrClass());
    assertEquals("UN1203", cargo.hazard().unNumber());
    assertTrue(cargo.requires(CargoTransportRequirement.ADR_VEHICLE_REQUIRED));
  }

  @Test
  void cargoCanHaveMultipleLogisticCategories() {
    var cargo = adrChemicalCargo();

    assertTrue(cargo.hasCategory(CargoCategory.ADR));
    assertTrue(cargo.hasCategory(CargoCategory.LIQUID));
    assertFalse(cargo.hasCategory(CargoCategory.FROZEN));
  }

  @Test
  void activeStatusIsAnagraphicAndNotShipmentStatus() {
    var cargo = generalPalletizedCargo();

    cargo.suspend();
    assertEquals(CargoStatus.SUSPENDED, cargo.status());

    cargo.archive();
    assertEquals(CargoStatus.ARCHIVED, cargo.status());
  }

  @Test
  void adrRegulatoryRequirementMustDeclareTransportRequirement() {
    assertThrows(
        InvalidCargoException.class,
        () ->
            new CargoUnit(
                null,
                CargoCode.of("ADR-999"),
                "Invalid ADR cargo",
                "ADR cargo missing transport requirement",
                CargoType.CHEMICAL,
                Set.of(CargoCategory.ADR, CargoCategory.LIQUID),
                CargoDimensions.empty(),
                CargoWeights.empty(),
                CargoPackaging.loose(),
                CargoTemperature.uncontrolled(),
                new CargoHazard("3", "UN1203", "II", "", ""),
                new CargoRegulatory(true, false, false, false, false, false, ""),
                new CargoProperties(false, false, true, false, false, ""),
                CargoCompatibilityRequirement.none(),
                CargoStatus.ACTIVE,
                ""));
  }

  @Test
  void controlledTemperatureRequiresTemperatureTransportRequirement() {
    assertThrows(
        InvalidCargoException.class,
        () ->
            new CargoUnit(
                null,
                CargoCode.of("TMP-001"),
                "Invalid temperature cargo",
                "Temperature cargo missing transport requirement",
                CargoType.FOOD,
                Set.of(CargoCategory.REFRIGERATED),
                CargoDimensions.empty(),
                CargoWeights.empty(),
                CargoPackaging.loose(),
                new CargoTemperature(new BigDecimal("2"), new BigDecimal("4"), true, ""),
                CargoHazard.none(),
                CargoRegulatory.none(),
                CargoProperties.standard(),
                CargoCompatibilityRequirement.none(),
                CargoStatus.ACTIVE,
                ""));
  }

  @Test
  void weightsCannotHaveNetGreaterThanGross() {
    assertThrows(
        InvalidCargoException.class,
        () -> new CargoWeights(new BigDecimal("100"), new BigDecimal("120"), BigDecimal.TEN));
  }

  @Test
  void failedTemperatureReplacementDoesNotMutateCargo() {
    var cargo = generalPalletizedCargo();
    var controlledTemperature =
        new CargoTemperature(new BigDecimal("2"), new BigDecimal("4"), true, "Fresh");

    assertThrows(
        InvalidCargoException.class, () -> cargo.replaceTemperature(controlledTemperature));

    assertFalse(cargo.isTemperatureControlled());
    assertEquals(CargoTemperature.uncontrolled(), cargo.temperature());
  }

  private static CargoUnit generalPalletizedCargo() {
    return new CargoUnit(
        null,
        CargoCode.of("cgo-001"),
        "Merce generica pallettizzata",
        "Merce secca generale su pallet",
        CargoType.GENERAL_GOODS,
        Set.of(CargoCategory.DRY, CargoCategory.PALLETIZED),
        new CargoDimensions(
            new BigDecimal("1.20"),
            new BigDecimal("0.80"),
            new BigDecimal("1.50"),
            new BigDecimal("1.44")),
        new CargoWeights(new BigDecimal("800"), new BigDecimal("760"), new BigDecimal("40")),
        new CargoPackaging(CargoPackagingType.PALLET, 1, 1, "", true, "Euro pallet"),
        CargoTemperature.uncontrolled(),
        CargoHazard.none(),
        CargoRegulatory.none(),
        CargoProperties.standard(),
        new CargoCompatibilityRequirement(
            Set.of(), new BigDecimal("800"), new BigDecimal("1.44"), null, null, null, ""),
        CargoStatus.ACTIVE,
        "Standard dry cargo");
  }

  private static CargoUnit refrigeratedFoodCargo() {
    return new CargoUnit(
        null,
        CargoCode.of("FOOD-023"),
        "Prodotti freschi alimentari",
        "Merce alimentare fresca a temperatura controllata",
        CargoType.FOOD,
        Set.of(CargoCategory.REFRIGERATED, CargoCategory.PALLETIZED),
        CargoDimensions.empty(),
        CargoWeights.empty(),
        new CargoPackaging(CargoPackagingType.PALLET, null, 10, "", true, ""),
        new CargoTemperature(new BigDecimal("2"), new BigDecimal("4"), true, "Fresh food"),
        CargoHazard.none(),
        new CargoRegulatory(false, true, true, false, false, false, "ATP and food grade"),
        new CargoProperties(false, true, false, false, false, "Perishable food"),
        new CargoCompatibilityRequirement(
            Set.of(
                CargoTransportRequirement.REFRIGERATED_VEHICLE_REQUIRED,
                CargoTransportRequirement.TEMPERATURE_CONTROL_REQUIRED,
                CargoTransportRequirement.ATP_CERTIFICATION_REQUIRED,
                CargoTransportRequirement.FOOD_GRADE_BODY_REQUIRED),
            null,
            null,
            null,
            null,
            null,
            "Requires refrigerated ATP transport"),
        CargoStatus.ACTIVE,
        "Fresh food cargo");
  }

  private static CargoUnit adrChemicalCargo() {
    return new CargoUnit(
        null,
        CargoCode.of("ADR-120"),
        "Liquido infiammabile",
        "Merce chimica liquida ADR",
        CargoType.CHEMICAL,
        Set.of(CargoCategory.ADR, CargoCategory.LIQUID),
        CargoDimensions.empty(),
        new CargoWeights(new BigDecimal("1000"), new BigDecimal("950"), new BigDecimal("50")),
        new CargoPackaging(CargoPackagingType.DRUM, 4, null, "", false, ""),
        CargoTemperature.uncontrolled(),
        new CargoHazard("3", "UN1203", "II", "", "Flammable liquid"),
        new CargoRegulatory(true, false, false, false, false, false, "ADR required"),
        new CargoProperties(false, false, true, false, true, "Dangerous liquid"),
        new CargoCompatibilityRequirement(
            Set.of(
                CargoTransportRequirement.ADR_VEHICLE_REQUIRED,
                CargoTransportRequirement.SEPARATION_REQUIRED),
            null,
            null,
            null,
            null,
            null,
            "ADR compatible vehicle required"),
        CargoStatus.ACTIVE,
        "ADR cargo");
  }
}
