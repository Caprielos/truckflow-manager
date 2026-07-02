package it.gabriele.truckflow.infrastructure.mapping;

import java.util.List;
import java.util.Optional;

/**
 * Catalog of the official Punto 7D persistence mapping blueprints for the current application
 * contexts.
 *
 * <p>The catalog documents the intended mapping direction before real persistence models or real
 * repository adapters are introduced.
 */
public final class PersistenceMappingBlueprintCatalog {

  private static final List<PersistenceMappingBlueprint> BLUEPRINTS =
      List.of(
          blueprint(
              "locations",
              "Location",
              "LocationPersistenceModel",
              "Locations are a safe pilot for future repository prototypes because they are stable"
                  + " logistic places with clear identity and code.",
              field("id", "id", PersistenceMappingKind.VALUE_OBJECT, true, "Stable LocationId."),
              field(
                  "code",
                  "code",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Stable business LocationCode."),
              field(
                  "type",
                  "type",
                  PersistenceMappingKind.ENUMERATION,
                  true,
                  "Persist as stable enum name."),
              field(
                  "status",
                  "status",
                  PersistenceMappingKind.STATE,
                  true,
                  "Persist as stable domain status."),
              field(
                  "address",
                  "address",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Flatten or embed address components without adding business rules.")),
          blueprint(
              "cargo",
              "CargoUnit",
              "CargoUnitPersistenceModel",
              "Cargo mapping must preserve transport requirements without calculating compatibility"
                  + " inside infrastructure.",
              field("id", "id", PersistenceMappingKind.VALUE_OBJECT, true, "Stable CargoId."),
              field(
                  "code",
                  "code",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Stable CargoCode."),
              field(
                  "status",
                  "status",
                  PersistenceMappingKind.STATE,
                  true,
                  "Cargo lifecycle status."),
              field(
                  "weights",
                  "weights",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Declared and technical weight values."),
              field(
                  "transportRequirements",
                  "transport_requirements",
                  PersistenceMappingKind.COLLECTION,
                  false,
                  "Persist requirements as technical records, not as infrastructure decisions.")),
          blueprint(
              "shipments",
              "Shipment",
              "ShipmentPersistenceModel",
              "Shipment mapping must keep items and legs as persisted children while rebuilding the"
                  + " aggregate through domain APIs.",
              field("id", "id", PersistenceMappingKind.VALUE_OBJECT, true, "Stable ShipmentId."),
              field(
                  "code",
                  "code",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Stable ShipmentCode."),
              field(
                  "status",
                  "status",
                  PersistenceMappingKind.STATE,
                  true,
                  "Shipment state machine value."),
              field(
                  "items",
                  "items",
                  PersistenceMappingKind.COLLECTION,
                  false,
                  "Shipment items persisted as child technical records."),
              field(
                  "legs",
                  "legs",
                  PersistenceMappingKind.COLLECTION,
                  false,
                  "Shipment legs persisted as ordered child technical records.")),
          blueprint(
              "documents",
              "Document",
              "DocumentPersistenceModel",
              "Document mapping remains logical only: no file upload, binary storage or PDF"
                  + " generation is introduced by Punto 7D.",
              field("id", "id", PersistenceMappingKind.VALUE_OBJECT, true, "Stable DocumentId."),
              field(
                  "code",
                  "code",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Stable DocumentCode."),
              field(
                  "status",
                  "status",
                  PersistenceMappingKind.STATE,
                  true,
                  "Logical document lifecycle state."),
              field(
                  "reference",
                  "reference",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Reference to the business object described by the document."),
              field(
                  "metadata",
                  "metadata",
                  PersistenceMappingKind.VALUE_OBJECT,
                  false,
                  "Technical metadata representation without storage concerns.")),
          blueprint(
              "vehicles.unit",
              "VehicleUnit",
              "VehicleUnitPersistenceModel",
              "Vehicle unit mapping keeps technical specifications separate from future operational"
                  + " availability or planning modules.",
              field(
                  "id", "id", PersistenceMappingKind.VALUE_OBJECT, true, "Stable VehicleUnitId."),
              field(
                  "fleetCode",
                  "fleet_code",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Stable business fleet code."),
              field(
                  "status",
                  "status",
                  PersistenceMappingKind.STATE,
                  true,
                  "Vehicle lifecycle status."),
              field(
                  "unitType",
                  "unit_type",
                  PersistenceMappingKind.ENUMERATION,
                  true,
                  "Vehicle unit type as stable enum name."),
              field(
                  "technicalSpecification",
                  "technical_specification",
                  PersistenceMappingKind.VALUE_OBJECT,
                  false,
                  "Technical specification may be embedded or separated later.")),
          blueprint(
              "vehicles.combination",
              "VehicleCombination",
              "VehicleCombinationPersistenceModel",
              "Vehicle combination mapping stores member vehicle IDs and does not introduce"
                  + " planning or dispatch assignment.",
              field(
                  "id",
                  "id",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Stable VehicleCombinationId."),
              field(
                  "code",
                  "code",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Stable combination code."),
              field(
                  "status",
                  "status",
                  PersistenceMappingKind.STATE,
                  true,
                  "Combination lifecycle state."),
              field(
                  "vehicleUnitIds",
                  "vehicle_unit_ids",
                  PersistenceMappingKind.REFERENCE,
                  true,
                  "References to vehicle units persisted by ID only.")),
          operationalBlueprint("operational.driver", "Driver", "DriverPersistenceModel"),
          operationalBlueprint("operational.mechanic", "Mechanic", "MechanicPersistenceModel"),
          operationalBlueprint(
              "operational.warehouse",
              "WarehouseOperator",
              "WarehouseOperatorPersistenceModel"),
          operationalBlueprint(
              "operational.dispatcher", "Dispatcher", "DispatcherPersistenceModel"),
          operationalBlueprint("operational.manager", "Manager", "ManagerPersistenceModel"),
          blueprint(
              "compliance",
              "ComplianceRequirement",
              "ComplianceRequirementPersistenceModel",
              "Compliance mapping stores abstract requirements only. It does not introduce country"
                  + " engines, deadline calculations or violation detection.",
              field(
                  "id",
                  "id",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Stable ComplianceRequirementId."),
              field(
                  "code",
                  "code",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Stable ComplianceRequirementCode."),
              field(
                  "status",
                  "status",
                  PersistenceMappingKind.STATE,
                  true,
                  "Requirement lifecycle state."),
              field(
                  "jurisdiction",
                  "jurisdiction",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Country and regional scope represented technically, not evaluated here."),
              field(
                  "target",
                  "target",
                  PersistenceMappingKind.VALUE_OBJECT,
                  true,
                  "Abstract target type and reference.")));

  private PersistenceMappingBlueprintCatalog() {}

  /** Returns all official mapping blueprints for the current Punto 7D scope. */
  public static List<PersistenceMappingBlueprint> all() {
    return BLUEPRINTS;
  }

  /** Finds a blueprint by its context name. */
  public static Optional<PersistenceMappingBlueprint> findByContext(String contextName) {
    if (contextName == null || contextName.isBlank()) {
      return Optional.empty();
    }
    String normalized = contextName.strip();
    return BLUEPRINTS.stream()
        .filter(blueprint -> blueprint.contextName().equals(normalized))
        .findFirst();
  }

  private static PersistenceMappingBlueprint operationalBlueprint(
      String contextName, String domainType, String persistenceModelName) {
    return blueprint(
        contextName,
        domainType,
        persistenceModelName,
        "Operational role mapping stores identity, business code, status and scopes without"
            + " introducing payroll, shifts or availability modules.",
        field("id", "id", PersistenceMappingKind.VALUE_OBJECT, true, "Stable role ID."),
        field(
            "code",
            "code",
            PersistenceMappingKind.VALUE_OBJECT,
            true,
            "Stable operational business code."),
        field("status", "status", PersistenceMappingKind.STATE, true, "Operational role state."),
        field(
            "scopes",
            "scopes",
            PersistenceMappingKind.COLLECTION,
            false,
            "Operational scopes persisted as technical child values."));
  }

  private static PersistenceMappingBlueprint blueprint(
      String contextName,
      String domainType,
      String persistenceModelName,
      String notes,
      PersistenceFieldMapping... fields) {
    return new PersistenceMappingBlueprint(
        contextName, domainType, persistenceModelName, List.of(fields), notes);
  }

  private static PersistenceFieldMapping field(
      String domainField,
      String persistenceField,
      PersistenceMappingKind kind,
      boolean mandatory,
      String notes) {
    return new PersistenceFieldMapping(domainField, persistenceField, kind, mandatory, notes);
  }
}
