package it.gabriele.truckflow.domain.deadlinepolicy;

import it.gabriele.truckflow.domain.deadline.DeadlineOwnerType;
import it.gabriele.truckflow.domain.deadline.DeadlineSeverity;
import it.gabriele.truckflow.domain.deadline.DeadlineType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Classe configurabile dove inserire le scadenze tecniche specifiche di camion, rimorchi e
 * componenti.
 *
 * <p>Questa classe è il punto da modificare quando arrivano i dati reali del costruttore o del
 * singolo modello. Le regole generiche coprono tutti gli elementi principali; le sezioni specifiche
 * per modello possono sovrascrivere gli intervalli di un elemento senza cambiare la logica del
 * dominio.
 */
public final class ConfigurableTechnicalDeadlineRuleBook {

  private static final Pattern UNSAFE_CODE_CHARS = Pattern.compile("[^A-Z0-9_-]");

  private ConfigurableTechnicalDeadlineRuleBook() {}

  public static List<DeadlinePolicyRule> rulesFor(String manufacturer, String modelFamily) {
    String normalizedManufacturer = normalizeToken(manufacturer);
    String normalizedModelFamily = normalizeToken(modelFamily);

    if (normalizedManufacturer.equals("IVECO") && normalizedModelFamily.contains("S-WAY")) {
      return mergeWithGenericDefaults(
          "IVECO",
          "S-WAY",
          List.of(
              vehicle(
                  "IVECO_SWAY_ENGINE_OIL",
                  "IVECO",
                  "S-WAY",
                  ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
                  DeadlineRuleInterval.monthsOrKilometers(12, 90000, 30),
                  "Olio motore Iveco S-Way - valore configurabile da piano costruttore"),
              vehicle(
                  "IVECO_SWAY_AIR_FILTER",
                  "IVECO",
                  "S-WAY",
                  ManagedDeadlineElementType.VEHICLE_AIR_FILTER,
                  DeadlineRuleInterval.monthsOrKilometers(12, 90000, 30),
                  "Filtro aria Iveco S-Way - valore configurabile da piano costruttore"),
              vehicle(
                  "IVECO_SWAY_OIL_FILTER",
                  "IVECO",
                  "S-WAY",
                  ManagedDeadlineElementType.VEHICLE_OIL_FILTER,
                  DeadlineRuleInterval.monthsOrKilometers(12, 90000, 30),
                  "Filtro olio Iveco S-Way - valore configurabile da piano costruttore"),
              vehicle(
                  "IVECO_SWAY_FUEL_FILTER",
                  "IVECO",
                  "S-WAY",
                  ManagedDeadlineElementType.VEHICLE_FUEL_FILTER,
                  DeadlineRuleInterval.monthsOrKilometers(12, 90000, 30),
                  "Filtro carburante Iveco S-Way - valore configurabile da piano costruttore"),
              vehicle(
                  "IVECO_SWAY_BRAKE_PADS",
                  "IVECO",
                  "S-WAY",
                  ManagedDeadlineElementType.VEHICLE_BRAKE_PADS,
                  DeadlineRuleInterval.monthsOrKilometers(12, 120000, 30),
                  "Pastiglie freno Iveco S-Way - valore configurabile da piano costruttore")));
    }

    if (normalizedManufacturer.equals("SCANIA") && normalizedModelFamily.contains("R")) {
      return mergeWithGenericDefaults(
          "SCANIA",
          "R-SERIES",
          List.of(
              vehicle(
                  "SCANIA_R_ENGINE_OIL",
                  "SCANIA",
                  "R-SERIES",
                  ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
                  DeadlineRuleInterval.monthsOrKilometers(18, 120000, 45),
                  "Olio motore Scania R - valore configurabile da piano costruttore"),
              vehicle(
                  "SCANIA_R_ADBLUE",
                  "SCANIA",
                  "R-SERIES",
                  ManagedDeadlineElementType.VEHICLE_ADBLUE_SYSTEM,
                  DeadlineRuleInterval.monthsOrKilometers(12, 90000, 30),
                  "Sistema AdBlue Scania R - valore configurabile da piano costruttore"),
              vehicle(
                  "SCANIA_R_SUSPENSION",
                  "SCANIA",
                  "R-SERIES",
                  ManagedDeadlineElementType.VEHICLE_SUSPENSION,
                  DeadlineRuleInterval.monthsOrKilometers(12, 100000, 30),
                  "Sospensioni Scania R - valore configurabile da piano costruttore")));
    }

    if (normalizedManufacturer.equals("VOLVO") && normalizedModelFamily.contains("FH")) {
      return mergeWithGenericDefaults(
          "VOLVO",
          "FH",
          List.of(
              vehicle(
                  "VOLVO_FH_ENGINE_OIL",
                  "VOLVO",
                  "FH",
                  ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
                  DeadlineRuleInterval.monthsOrKilometers(12, 100000, 30),
                  "Olio motore Volvo FH - valore configurabile da piano costruttore"),
              vehicle(
                  "VOLVO_FH_ELECTRICAL",
                  "VOLVO",
                  "FH",
                  ManagedDeadlineElementType.VEHICLE_ELECTRICAL_SYSTEM,
                  DeadlineRuleInterval.calendarMonths(12, 30),
                  "Impianto elettrico Volvo FH - valore configurabile da piano costruttore")));
    }

    if (normalizedManufacturer.equals("SCHMITZ")
        || normalizedManufacturer.equals("SCHMITZ CARGOBULL")
        || normalizedManufacturer.equals("SCHMITZ_CARGOBULL")
        || normalizedManufacturer.equals("SCHMITZ-CARGOBULL")) {
      return mergeWithGenericDefaults(
          "SCHMITZ CARGOBULL",
          normalizedModelFamily,
          List.of(
              trailer(
                  "SCHMITZ_TRAILER_REFRIGERATION",
                  "SCHMITZ CARGOBULL",
                  normalizedModelFamily,
                  ManagedDeadlineElementType.TRAILER_REFRIGERATION_UNIT,
                  DeadlineRuleInterval.monthsOrRefrigerationHours(6, 1000, 30),
                  "Impianto refrigerante semirimorchio Schmitz - valore configurabile"),
              trailer(
                  "SCHMITZ_TRAILER_BRAKING_SYSTEM",
                  "SCHMITZ CARGOBULL",
                  normalizedModelFamily,
                  ManagedDeadlineElementType.TRAILER_BRAKING_SYSTEM,
                  DeadlineRuleInterval.calendarMonths(12, 30),
                  "Impianto frenante semirimorchio Schmitz - valore configurabile")));
    }

    return genericRules(normalizedManufacturer, normalizedModelFamily);
  }

  private static List<DeadlinePolicyRule> mergeWithGenericDefaults(
      String manufacturer, String modelFamily, List<DeadlinePolicyRule> specificRules) {
    List<DeadlinePolicyRule> result = new ArrayList<>(specificRules);
    Set<ManagedDeadlineElementType> configuredElements = new LinkedHashSet<>();
    specificRules.forEach(rule -> configuredElements.add(rule.getElementType()));

    genericRules(manufacturer, modelFamily).stream()
        .filter(rule -> !configuredElements.contains(rule.getElementType()))
        .forEach(result::add);

    return List.copyOf(result);
  }

  private static List<DeadlinePolicyRule> genericRules(String manufacturer, String modelFamily) {
    List<DeadlinePolicyRule> rules = new ArrayList<>();
    rules.addAll(genericTruckRules(manufacturer, modelFamily));
    rules.addAll(genericTrailerRules(manufacturer, modelFamily));
    return List.copyOf(rules);
  }

  private static List<DeadlinePolicyRule> genericTruckRules(
      String manufacturer, String modelFamily) {
    return List.of(
        vehicle(
            technicalCode(manufacturer, modelFamily, "ENGINE_OIL"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
            DeadlineRuleInterval.monthsOrKilometers(12, 80000, 30),
            "Olio motore - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "AIR_FILTER"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_AIR_FILTER,
            DeadlineRuleInterval.monthsOrKilometers(12, 80000, 30),
            "Filtro aria - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "OIL_FILTER"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_OIL_FILTER,
            DeadlineRuleInterval.monthsOrKilometers(12, 80000, 30),
            "Filtro olio - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "FUEL_FILTER"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_FUEL_FILTER,
            DeadlineRuleInterval.monthsOrKilometers(12, 80000, 30),
            "Filtro carburante - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "BRAKE_PADS"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_BRAKE_PADS,
            DeadlineRuleInterval.monthsOrKilometers(12, 80000, 30),
            "Pastiglie freno - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "BRAKE_DISCS"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_BRAKE_DISCS,
            DeadlineRuleInterval.monthsOrKilometers(24, 160000, 45),
            "Dischi freno - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "COOLANT"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_COOLANT,
            DeadlineRuleInterval.monthsOrKilometers(24, 160000, 45),
            "Liquido refrigerante - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "ADBLUE_SYSTEM"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_ADBLUE_SYSTEM,
            DeadlineRuleInterval.monthsOrKilometers(12, 80000, 30),
            "Sistema AdBlue - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "BELTS"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_BELTS,
            DeadlineRuleInterval.monthsOrKilometers(24, 160000, 45),
            "Cinghie distribuzione/servizi - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "BATTERY"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_BATTERY,
            DeadlineRuleInterval.calendarMonths(36, 45),
            "Batteria - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "SUSPENSION"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_SUSPENSION,
            DeadlineRuleInterval.monthsOrKilometers(12, 100000, 30),
            "Sospensioni - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "LIGHTS"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_LIGHTS,
            DeadlineRuleInterval.calendarMonths(6, 15),
            "Luci e dispositivi luminosi - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "ENGINE_DIAGNOSTIC"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_ENGINE_DIAGNOSTIC,
            DeadlineRuleInterval.monthsOrKilometers(3, 25000, 15),
            "Diagnostica motore e DTC - regola tecnica generica da personalizzare"),
        vehicle(
            technicalCode(manufacturer, modelFamily, "ELECTRICAL_SYSTEM"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.VEHICLE_ELECTRICAL_SYSTEM,
            DeadlineRuleInterval.calendarMonths(12, 30),
            "Impianto elettrico veicolo - regola tecnica generica da personalizzare"));
  }

  private static List<DeadlinePolicyRule> genericTrailerRules(
      String manufacturer, String modelFamily) {
    return List.of(
        trailer(
            technicalCode(manufacturer, modelFamily, "TRAILER_BRAKING_SYSTEM"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.TRAILER_BRAKING_SYSTEM,
            DeadlineRuleInterval.calendarMonths(12, 30),
            "Impianto frenante rimorchio - regola tecnica generica da personalizzare"),
        trailer(
            technicalCode(manufacturer, modelFamily, "TRAILER_ELECTRICAL_SYSTEM"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.TRAILER_ELECTRICAL_SYSTEM,
            DeadlineRuleInterval.calendarMonths(12, 30),
            "Impianto elettrico rimorchio - regola tecnica generica da personalizzare"),
        trailer(
            technicalCode(manufacturer, modelFamily, "TRAILER_REFRIGERATION_UNIT"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.TRAILER_REFRIGERATION_UNIT,
            DeadlineRuleInterval.monthsOrRefrigerationHours(6, 1000, 30),
            "Impianto refrigerante ATP/rimorchio frigo - regola tecnica generica"),
        trailer(
            technicalCode(manufacturer, modelFamily, "TRAILER_BODY_FLOOR"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.TRAILER_BODY_FLOOR,
            DeadlineRuleInterval.calendarMonths(12, 30),
            "Pianale/cassone rimorchio - regola tecnica generica da personalizzare"),
        trailer(
            technicalCode(manufacturer, modelFamily, "TRAILER_DOORS_LOCKS"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.TRAILER_DOORS_LOCKS,
            DeadlineRuleInterval.calendarMonths(6, 15),
            "Porte, serrature e chiusure rimorchio - regola tecnica generica"),
        trailer(
            technicalCode(manufacturer, modelFamily, "TRAILER_FIFTH_WHEEL_COUPLING"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.TRAILER_FIFTH_WHEEL_COUPLING,
            DeadlineRuleInterval.calendarMonths(6, 15),
            "Ralla, perno e accoppiamento - regola tecnica generica da personalizzare"),
        trailer(
            technicalCode(manufacturer, modelFamily, "TRAILER_LANDING_GEAR"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.TRAILER_LANDING_GEAR,
            DeadlineRuleInterval.calendarMonths(6, 15),
            "Piedini/attacchi rimorchio - regola tecnica generica da personalizzare"),
        trailer(
            technicalCode(manufacturer, modelFamily, "TRAILER_TAIL_LIFT"),
            manufacturer,
            modelFamily,
            ManagedDeadlineElementType.TRAILER_TAIL_LIFT,
            DeadlineRuleInterval.monthsOrEngineHours(6, 500, 30),
            "Sponde idrauliche - regola tecnica generica da personalizzare"));
  }

  private static DeadlinePolicyRule vehicle(
      String ruleCode,
      String manufacturer,
      String modelFamily,
      ManagedDeadlineElementType elementType,
      DeadlineRuleInterval interval,
      String reference) {
    return rule(
        ruleCode,
        manufacturer,
        modelFamily,
        DeadlineOwnerType.VEHICLE,
        elementType,
        interval,
        reference);
  }

  private static DeadlinePolicyRule trailer(
      String ruleCode,
      String manufacturer,
      String modelFamily,
      ManagedDeadlineElementType elementType,
      DeadlineRuleInterval interval,
      String reference) {
    return rule(
        ruleCode,
        manufacturer,
        modelFamily,
        DeadlineOwnerType.TRAILER,
        elementType,
        interval,
        reference);
  }

  private static DeadlinePolicyRule rule(
      String ruleCode,
      String manufacturer,
      String modelFamily,
      DeadlineOwnerType ownerType,
      ManagedDeadlineElementType elementType,
      DeadlineRuleInterval interval,
      String reference) {
    return DeadlinePolicyRule.technical(
        ruleCode,
        manufacturer,
        modelFamily,
        ownerType,
        elementType,
        DeadlineType.SCHEDULED_MAINTENANCE,
        interval,
        DeadlineSeverity.HIGH,
        true,
        reference);
  }

  private static String technicalCode(String manufacturer, String modelFamily, String suffix) {
    String manufacturerToken = sanitizeCodePart(manufacturer);
    String modelToken = sanitizeCodePart(modelFamily);
    String suffixToken = sanitizeCodePart(suffix);
    String code = manufacturerToken + "_" + modelToken + "_" + suffixToken;
    if (code.length() <= 80) {
      return code;
    }
    String shorterCode = manufacturerToken + "_" + suffixToken;
    if (shorterCode.length() <= 80) {
      return shorterCode;
    }
    return shorterCode.substring(0, 80);
  }

  private static String normalizeToken(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Costruttore e modello sono obbligatori.");
    }
    return value.trim().toUpperCase(Locale.ROOT);
  }

  private static String sanitizeCodePart(String value) {
    return UNSAFE_CODE_CHARS.matcher(normalizeToken(value).replace(' ', '_')).replaceAll("_");
  }
}
