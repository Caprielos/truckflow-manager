package it.gabriele.truckflow.deadlineservice.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Catalogo completo degli elementi che il futuro microservizio scadenze deve conoscere. */
public final class ManagedElementCatalog {
  private static final Map<ManagedElementCode, ManagedElementDefinition> DEFINITIONS = build();

  private ManagedElementCatalog() {}

  public static List<ManagedElementDefinition> all() {
    return List.copyOf(DEFINITIONS.values());
  }

  public static ManagedElementDefinition require(ManagedElementCode code) {
    ManagedElementDefinition definition = DEFINITIONS.get(code);
    if (definition == null) {
      throw new IllegalArgumentException("Elemento gestito non censito: " + code);
    }
    return definition;
  }

  public static Optional<ManagedElementDefinition> find(ManagedElementCode code) {
    return Optional.ofNullable(DEFINITIONS.get(code));
  }

  public static List<ManagedElementDefinition> byCategory(ManagedElementCategory category) {
    return DEFINITIONS.values().stream()
        .filter(definition -> definition.category() == category)
        .toList();
  }

  public static List<ManagedElementDefinition> requiringSource(DeadlineRuleSourceType sourceType) {
    return DEFINITIONS.values().stream()
        .filter(definition -> definition.expectedSources().contains(sourceType))
        .toList();
  }

  public static boolean contains(ManagedElementCode code) {
    return DEFINITIONS.containsKey(code);
  }

  private static Map<ManagedElementCode, ManagedElementDefinition> build() {
    List<ManagedElementDefinition> definitions = new ArrayList<>();

    registerVehicleDocuments(definitions);
    registerDriverDocuments(definitions);
    registerVehicleMaintenance(definitions);
    registerTrailerComponents(definitions);
    registerCargo(definitions);
    registerWarehouse(definitions);
    registerTrip(definitions);
    registerSecurity(definitions);
    registerTelematics(definitions);

    Map<ManagedElementCode, ManagedElementDefinition> byCode =
        new EnumMap<>(ManagedElementCode.class);
    for (ManagedElementDefinition definition : definitions) {
      ManagedElementDefinition previous = byCode.put(definition.code(), definition);
      if (previous != null) {
        throw new IllegalStateException("Elemento gestito duplicato: " + definition.code());
      }
    }

    EnumSet<ManagedElementCode> missing = EnumSet.allOf(ManagedElementCode.class);
    missing.removeAll(byCode.keySet());
    if (!missing.isEmpty()) {
      throw new IllegalStateException("Elementi gestiti senza definizione: " + missing);
    }

    return Collections.unmodifiableMap(byCode);
  }

  private static void registerVehicleDocuments(List<ManagedElementDefinition> definitions) {
    definitions.add(
        ManagedElementDefinition.legal(
            ManagedElementCode.VEHICLE_REGISTRATION_DOCUMENT,
            ManagedElementCategory.VEHICLE_DOCUMENT,
            ManagedElementOwnerType.VEHICLE));
    definitions.add(
        ManagedElementDefinition.legal(
            ManagedElementCode.VEHICLE_INSURANCE,
            ManagedElementCategory.VEHICLE_DOCUMENT,
            ManagedElementOwnerType.VEHICLE));
    definitions.add(
        ManagedElementDefinition.legal(
            ManagedElementCode.VEHICLE_ROADWORTHINESS_TEST,
            ManagedElementCategory.VEHICLE_DOCUMENT,
            ManagedElementOwnerType.VEHICLE));
    definitions.add(
        ManagedElementDefinition.legalAndTechnical(
            ManagedElementCode.VEHICLE_TACHOGRAPH_DEVICE_DEADLINE,
            ManagedElementCategory.VEHICLE_DOCUMENT,
            ManagedElementOwnerType.VEHICLE));
    definitions.add(
        ManagedElementDefinition.legalAndTechnical(
            ManagedElementCode.VEHICLE_ATP_CERTIFICATION,
            ManagedElementCategory.VEHICLE_DOCUMENT,
            ManagedElementOwnerType.VEHICLE));
    definitions.add(
        ManagedElementDefinition.legalAndTechnical(
            ManagedElementCode.VEHICLE_ADR_CERTIFICATION,
            ManagedElementCategory.VEHICLE_DOCUMENT,
            ManagedElementOwnerType.VEHICLE));
  }

  private static void registerDriverDocuments(List<ManagedElementDefinition> definitions) {
    definitions.add(
        ManagedElementDefinition.legal(
            ManagedElementCode.DRIVER_LICENSE,
            ManagedElementCategory.DRIVER_DOCUMENT,
            ManagedElementOwnerType.DRIVER));
    definitions.add(
        ManagedElementDefinition.legal(
            ManagedElementCode.DRIVER_CQC,
            ManagedElementCategory.DRIVER_DOCUMENT,
            ManagedElementOwnerType.DRIVER));
    definitions.add(
        ManagedElementDefinition.legal(
            ManagedElementCode.DRIVER_TACHOGRAPH_CARD,
            ManagedElementCategory.DRIVER_DOCUMENT,
            ManagedElementOwnerType.DRIVER));
    definitions.add(
        ManagedElementDefinition.legal(
            ManagedElementCode.DRIVER_MEDICAL_CHECK,
            ManagedElementCategory.DRIVER_DOCUMENT,
            ManagedElementOwnerType.DRIVER));
    definitions.add(
        ManagedElementDefinition.legal(
            ManagedElementCode.DRIVER_MANDATORY_TRAINING,
            ManagedElementCategory.DRIVER_DOCUMENT,
            ManagedElementOwnerType.DRIVER));
  }

  private static void registerVehicleMaintenance(List<ManagedElementDefinition> definitions) {
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_ENGINE_OIL);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_FILTERS);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_AIR_FILTER);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_OIL_FILTER);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_FUEL_FILTER);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_BRAKES);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_BRAKE_PADS);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_BRAKE_DISCS);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_COOLANT);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_ADBLUE_SYSTEM);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_BELTS);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_BATTERY);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_SUSPENSION);
    addTechnicalVehicle(definitions, ManagedElementCode.VEHICLE_LIGHTS);
    definitions.add(
        ManagedElementDefinition.monitored(
            ManagedElementCode.VEHICLE_ENGINE_DIAGNOSTIC,
            ManagedElementCategory.VEHICLE_MAINTENANCE,
            ManagedElementOwnerType.VEHICLE,
            false,
            true));
  }

  private static void registerTrailerComponents(List<ManagedElementDefinition> definitions) {
    addTechnicalTrailer(definitions, ManagedElementCode.TRAILER_BRAKING_SYSTEM, true);
    addTechnicalTrailer(definitions, ManagedElementCode.TRAILER_ELECTRICAL_SYSTEM, true);
    addTechnicalTrailer(definitions, ManagedElementCode.TRAILER_REFRIGERATION_UNIT, true);
    addTechnicalTrailer(definitions, ManagedElementCode.TRAILER_BODY_FLOOR, false);
    addTechnicalTrailer(definitions, ManagedElementCode.TRAILER_DOORS_LOCKS, true);
    addTechnicalTrailer(definitions, ManagedElementCode.TRAILER_FIFTH_WHEEL_COUPLING, true);
    addTechnicalTrailer(definitions, ManagedElementCode.TRAILER_TAIL_LIFT, true);
    addTechnicalTrailer(definitions, ManagedElementCode.TRAILER_LANDING_GEAR, true);
  }

  private static void registerCargo(List<ManagedElementDefinition> definitions) {
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.CARGO_PALLET,
            ManagedElementCategory.CARGO,
            ManagedElementOwnerType.CARGO,
            false,
            false));
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.CARGO_CONTAINER,
            ManagedElementCategory.CARGO,
            ManagedElementOwnerType.CARGO,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.CARGO_PACKAGING,
            ManagedElementCategory.CARGO,
            ManagedElementOwnerType.CARGO,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.security(
            ManagedElementCode.CARGO_SEAL,
            ManagedElementCategory.CARGO,
            ManagedElementOwnerType.CARGO,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.CARGO_LABEL,
            ManagedElementCategory.CARGO,
            ManagedElementOwnerType.CARGO,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.CARGO_DECLARED_VS_ACTUAL_WEIGHT,
            ManagedElementCategory.CARGO,
            ManagedElementOwnerType.CARGO,
            true,
            true));
  }

  private static void registerWarehouse(List<ManagedElementDefinition> definitions) {
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.WAREHOUSE_LOCATION,
            ManagedElementCategory.WAREHOUSE,
            ManagedElementOwnerType.WAREHOUSE,
            false,
            false));
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.WAREHOUSE_AISLES_RACKS,
            ManagedElementCategory.WAREHOUSE,
            ManagedElementOwnerType.WAREHOUSE,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.monitored(
            ManagedElementCode.WAREHOUSE_COLD_CELL,
            ManagedElementCategory.WAREHOUSE,
            ManagedElementOwnerType.WAREHOUSE,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.technical(
            ManagedElementCode.WAREHOUSE_EQUIPMENT,
            ManagedElementCategory.WAREHOUSE,
            ManagedElementOwnerType.WAREHOUSE));
    definitions.add(
        ManagedElementDefinition.security(
            ManagedElementCode.WAREHOUSE_SAFETY_SYSTEM,
            ManagedElementCategory.WAREHOUSE,
            ManagedElementOwnerType.WAREHOUSE,
            true,
            true));
  }

  private static void registerTrip(List<ManagedElementDefinition> definitions) {
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.TRIP_POINTS_OF_INTEREST,
            ManagedElementCategory.TRIP,
            ManagedElementOwnerType.TRIP,
            false,
            false));
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.TRIP_ROAD_RESTRICTION,
            ManagedElementCategory.TRIP,
            ManagedElementOwnerType.TRIP,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.monitored(
            ManagedElementCode.TRIP_WEATHER_CONDITION,
            ManagedElementCategory.TRIP,
            ManagedElementOwnerType.TRIP,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.operational(
            ManagedElementCode.TRIP_TOLL,
            ManagedElementCategory.TRIP,
            ManagedElementOwnerType.TRIP,
            false,
            false));
    definitions.add(
        ManagedElementDefinition.monitored(
            ManagedElementCode.TRIP_ESTIMATED_VS_ACTUAL_TIME,
            ManagedElementCategory.TRIP,
            ManagedElementOwnerType.TRIP,
            false,
            false));
  }

  private static void registerSecurity(List<ManagedElementDefinition> definitions) {
    definitions.add(
        ManagedElementDefinition.security(
            ManagedElementCode.SECURITY_LOCK,
            ManagedElementCategory.SECURITY,
            ManagedElementOwnerType.SECURITY_DEVICE,
            false,
            true));
    definitions.add(
        ManagedElementDefinition.security(
            ManagedElementCode.SECURITY_ALARM,
            ManagedElementCategory.SECURITY,
            ManagedElementOwnerType.SECURITY_DEVICE,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.security(
            ManagedElementCode.SECURITY_INTRUSION_SENSOR,
            ManagedElementCategory.SECURITY,
            ManagedElementOwnerType.SECURITY_DEVICE,
            true,
            true));
    definitions.add(
        ManagedElementDefinition.security(
            ManagedElementCode.SECURITY_ONBOARD_CAMERA,
            ManagedElementCategory.SECURITY,
            ManagedElementOwnerType.SECURITY_DEVICE,
            true,
            true));
  }

  private static void registerTelematics(List<ManagedElementDefinition> definitions) {
    addMonitoredTelematics(definitions, ManagedElementCode.TELEMATICS_CANBUS_SENSOR, false, false);
    addMonitoredTelematics(
        definitions, ManagedElementCode.TELEMATICS_DTC_ENGINE_ERROR, false, true);
    addMonitoredTelematics(definitions, ManagedElementCode.TELEMATICS_TEMPERATURE, true, true);
    addMonitoredTelematics(definitions, ManagedElementCode.TELEMATICS_TPMS_PRESSURE, false, true);
    addMonitoredTelematics(
        definitions, ManagedElementCode.TELEMATICS_REAL_FUEL_CONSUMPTION, false, false);
    addMonitoredTelematics(definitions, ManagedElementCode.TELEMATICS_DOOR_STATUS, true, true);
    addMonitoredTelematics(
        definitions, ManagedElementCode.TELEMATICS_UNAUTHORIZED_OPENING, true, true);
  }

  private static void addTechnicalVehicle(
      List<ManagedElementDefinition> definitions, ManagedElementCode code) {
    definitions.add(
        ManagedElementDefinition.technical(
            code, ManagedElementCategory.VEHICLE_MAINTENANCE, ManagedElementOwnerType.VEHICLE));
  }

  private static void addTechnicalTrailer(
      List<ManagedElementDefinition> definitions,
      ManagedElementCode code,
      boolean canBlockOperations) {
    definitions.add(
        new ManagedElementDefinition(
            code,
            ManagedElementCategory.TRAILER_COMPONENT,
            ManagedElementOwnerType.TRAILER,
            EnumSet.of(
                DeadlineRuleSourceType.MANUFACTURER_RULEBOOK,
                DeadlineRuleSourceType.INTERNAL_OPERATIONAL_POLICY),
            false,
            true,
            true,
            false,
            canBlockOperations,
            canBlockOperations));
  }

  private static void addMonitoredTelematics(
      List<ManagedElementDefinition> definitions,
      ManagedElementCode code,
      boolean requiresSecurityControl,
      boolean canBlockOperations) {
    definitions.add(
        ManagedElementDefinition.monitored(
            code,
            ManagedElementCategory.TELEMATICS,
            ManagedElementOwnerType.TELEMETRY_DEVICE,
            requiresSecurityControl,
            canBlockOperations));
  }
}
