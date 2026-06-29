package it.gabriele.truckflow.domain.deadlinepolicy;

import it.gabriele.truckflow.domain.deadline.DeadlineOwnerType;
import it.gabriele.truckflow.domain.deadline.DeadlineSeverity;
import it.gabriele.truckflow.domain.deadline.DeadlineType;
import java.util.List;
import java.util.Locale;

/** Catalogo tecnico iniziale delle scadenze definite da costruttore e modello. */
public final class ManufacturerTechnicalDeadlineCatalog {

  private ManufacturerTechnicalDeadlineCatalog() {}

  public static List<DeadlinePolicyRule> rulesFor(String manufacturer, String modelFamily) {
    String normalizedManufacturer = normalize(manufacturer);
    String normalizedModelFamily = normalize(modelFamily);

    if (normalizedManufacturer.equals("IVECO") && normalizedModelFamily.contains("S-WAY")) {
      return ivecoSWayRules();
    }
    if (normalizedManufacturer.equals("SCANIA") && normalizedModelFamily.contains("R")) {
      return scaniaRSeriesRules();
    }
    if (normalizedManufacturer.equals("VOLVO") && normalizedModelFamily.contains("FH")) {
      return volvoFhRules();
    }
    if (normalizedManufacturer.equals("SCHMITZ")
        || normalizedManufacturer.equals("SCHMITZ CARGOBULL")) {
      return refrigeratedTrailerRules(normalizedManufacturer, normalizedModelFamily);
    }

    return genericHeavyVehicleRules(normalizedManufacturer, normalizedModelFamily);
  }

  private static List<DeadlinePolicyRule> ivecoSWayRules() {
    return List.of(
        technicalVehicle(
            "IVECO_SWAY_ENGINE_OIL",
            "IVECO",
            "S-WAY",
            ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
            DeadlineRuleInterval.monthsOrKilometers(12, 90000, 30),
            "Piano manutenzione Iveco S-Way - configurazione iniziale"),
        technicalVehicle(
            "IVECO_SWAY_FILTERS",
            "IVECO",
            "S-WAY",
            ManagedDeadlineElementType.VEHICLE_AIR_FILTER,
            DeadlineRuleInterval.monthsOrKilometers(12, 90000, 30),
            "Filtri aria/olio/carburante Iveco S-Way - configurazione iniziale"),
        technicalVehicle(
            "IVECO_SWAY_BRAKES",
            "IVECO",
            "S-WAY",
            ManagedDeadlineElementType.VEHICLE_BRAKE_PADS,
            DeadlineRuleInterval.monthsOrKilometers(12, 120000, 30),
            "Controllo freni Iveco S-Way - configurazione iniziale"));
  }

  private static List<DeadlinePolicyRule> scaniaRSeriesRules() {
    return List.of(
        technicalVehicle(
            "SCANIA_R_ENGINE_OIL",
            "SCANIA",
            "R-SERIES",
            ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
            DeadlineRuleInterval.monthsOrKilometers(18, 120000, 45),
            "Piano manutenzione Scania R - configurazione iniziale"),
        technicalVehicle(
            "SCANIA_R_ADBLUE",
            "SCANIA",
            "R-SERIES",
            ManagedDeadlineElementType.VEHICLE_ADBLUE_SYSTEM,
            DeadlineRuleInterval.monthsOrKilometers(12, 90000, 30),
            "Controllo AdBlue Scania R - configurazione iniziale"),
        technicalVehicle(
            "SCANIA_R_SUSPENSION",
            "SCANIA",
            "R-SERIES",
            ManagedDeadlineElementType.VEHICLE_SUSPENSION,
            DeadlineRuleInterval.monthsOrKilometers(12, 100000, 30),
            "Controllo sospensioni Scania R - configurazione iniziale"));
  }

  private static List<DeadlinePolicyRule> volvoFhRules() {
    return List.of(
        technicalVehicle(
            "VOLVO_FH_ENGINE_OIL",
            "VOLVO",
            "FH",
            ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
            DeadlineRuleInterval.monthsOrKilometers(12, 100000, 30),
            "Piano manutenzione Volvo FH - configurazione iniziale"),
        technicalVehicle(
            "VOLVO_FH_ELECTRICAL",
            "VOLVO",
            "FH",
            ManagedDeadlineElementType.VEHICLE_ELECTRICAL_SYSTEM,
            DeadlineRuleInterval.calendarMonths(12, 30),
            "Controllo impianto elettrico Volvo FH - configurazione iniziale"));
  }

  private static List<DeadlinePolicyRule> refrigeratedTrailerRules(
      String manufacturer, String modelFamily) {
    return List.of(
        DeadlinePolicyRule.technical(
            "SCHMITZ_TRAILER_REFRIGERATION",
            manufacturer,
            modelFamily,
            DeadlineOwnerType.TRAILER,
            ManagedDeadlineElementType.TRAILER_REFRIGERATION_UNIT,
            DeadlineType.SCHEDULED_MAINTENANCE,
            DeadlineRuleInterval.monthsOrRefrigerationHours(6, 1000, 30),
            DeadlineSeverity.HIGH,
            true,
            "Manutenzione impianto refrigerante semirimorchio - configurazione iniziale"),
        DeadlinePolicyRule.technical(
            "SCHMITZ_TRAILER_BRAKES",
            manufacturer,
            modelFamily,
            DeadlineOwnerType.TRAILER,
            ManagedDeadlineElementType.TRAILER_BRAKING_SYSTEM,
            DeadlineType.SCHEDULED_MAINTENANCE,
            DeadlineRuleInterval.calendarMonths(12, 30),
            DeadlineSeverity.HIGH,
            true,
            "Controllo impianto frenante semirimorchio - configurazione iniziale"));
  }

  private static List<DeadlinePolicyRule> genericHeavyVehicleRules(
      String manufacturer, String modelFamily) {
    return List.of(
        technicalVehicle(
            manufacturer + "_" + modelFamily + "_ENGINE_OIL",
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
            DeadlineRuleInterval.monthsOrKilometers(12, 80000, 30),
            "Piano manutenzione generico costruttore/modello - configurazione iniziale"),
        technicalVehicle(
            manufacturer + "_" + modelFamily + "_BRAKES",
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_BRAKE_PADS,
            DeadlineRuleInterval.calendarMonths(12, 30),
            "Controllo freni generico costruttore/modello - configurazione iniziale"));
  }

  private static DeadlinePolicyRule technicalVehicle(
      String ruleCode,
      String manufacturer,
      String modelFamily,
      ManagedDeadlineElementType elementType,
      DeadlineRuleInterval interval,
      String reference) {
    return DeadlinePolicyRule.technical(
        ruleCode,
        manufacturer,
        modelFamily,
        DeadlineOwnerType.VEHICLE,
        elementType,
        DeadlineType.SCHEDULED_MAINTENANCE,
        interval,
        DeadlineSeverity.HIGH,
        true,
        reference);
  }

  private static String normalize(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Costruttore e modello sono obbligatori.");
    }
    return value.trim().toUpperCase(Locale.ROOT);
  }
}
