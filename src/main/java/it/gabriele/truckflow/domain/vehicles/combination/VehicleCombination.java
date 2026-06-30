package it.gabriele.truckflow.domain.vehicles.combination;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import it.gabriele.truckflow.domain.vehicles.exceptions.InvalidVehicleCombinationException;
import it.gabriele.truckflow.domain.vehicles.operation.VehicleCapability;
import it.gabriele.truckflow.domain.vehicles.operation.VehicleOperationalRole;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleStatus;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnit;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitType;
import java.util.HashSet;
import java.util.Set;

public final class VehicleCombination {

  private final VehicleCombinationId id;
  private final VehicleCombinationType combinationType;
  private final VehicleUnitId primaryUnitId;
  private final VehicleUnitId secondaryUnitId;
  private Set<VehicleCapability> combinedCapabilities;
  private Set<VehicleOperationalRole> operationalRoles;
  private VehicleStatus status;
  private String notes;

  public VehicleCombination(
      VehicleCombinationId id,
      VehicleCombinationType combinationType,
      VehicleUnitId primaryUnitId,
      VehicleUnitId secondaryUnitId,
      Set<VehicleCapability> combinedCapabilities,
      Set<VehicleOperationalRole> operationalRoles,
      VehicleStatus status,
      String notes) {
    this.id = id == null ? VehicleCombinationId.random() : id;
    this.combinationType = VehicleValidation.requireNonNull(combinationType, "combinationType");
    this.primaryUnitId = VehicleValidation.requireNonNull(primaryUnitId, "primaryUnitId");
    this.secondaryUnitId = secondaryUnitId;
    this.combinedCapabilities = validateCapabilities(combinedCapabilities);
    this.operationalRoles = validateOperationalRoles(operationalRoles);
    this.status = VehicleValidation.requireNonNull(status, "status");
    this.notes = VehicleValidation.normalize(notes);

    validateShape();
  }

  public static VehicleCombination fromUnits(
      VehicleCombinationId id,
      VehicleCombinationType combinationType,
      VehicleUnit primaryUnit,
      VehicleUnit secondaryUnit,
      VehicleStatus status,
      String notes) {
    VehicleValidation.requireNonNull(primaryUnit, "primaryUnit");
    validateUnitTypes(combinationType, primaryUnit, secondaryUnit);

    return new VehicleCombination(
        id,
        combinationType,
        primaryUnit.id(),
        secondaryUnit == null ? null : secondaryUnit.id(),
        combineCapabilities(primaryUnit, secondaryUnit),
        combineOperationalRoles(primaryUnit, secondaryUnit),
        status,
        notes);
  }

  public VehicleCombinationId id() {
    return id;
  }

  public VehicleCombinationType combinationType() {
    return combinationType;
  }

  public VehicleUnitId primaryUnitId() {
    return primaryUnitId;
  }

  public VehicleUnitId secondaryUnitId() {
    return secondaryUnitId;
  }

  public boolean hasSecondaryUnit() {
    return secondaryUnitId != null;
  }

  public Set<VehicleCapability> combinedCapabilities() {
    return Set.copyOf(combinedCapabilities);
  }

  public Set<VehicleOperationalRole> operationalRoles() {
    return Set.copyOf(operationalRoles);
  }

  public VehicleStatus status() {
    return status;
  }

  public String notes() {
    return notes;
  }

  public boolean hasCapability(VehicleCapability capability) {
    VehicleValidation.requireNonNull(capability, "capability");
    return combinedCapabilities.contains(capability);
  }

  public boolean hasOperationalRole(VehicleOperationalRole role) {
    VehicleValidation.requireNonNull(role, "role");
    return operationalRoles.contains(role);
  }

  public boolean isActive() {
    return status == VehicleStatus.ACTIVE;
  }

  public void addCapability(VehicleCapability capability) {
    VehicleValidation.requireNonNull(capability, "capability");
    var updatedCapabilities = new HashSet<>(combinedCapabilities);
    updatedCapabilities.add(capability);
    combinedCapabilities = validateCapabilities(updatedCapabilities);
  }

  public void activate() {
    status = VehicleStatus.ACTIVE;
  }

  public void suspend() {
    status = VehicleStatus.SUSPENDED;
  }

  public void markOutOfService() {
    status = VehicleStatus.OUT_OF_SERVICE;
  }

  public void dismiss() {
    status = VehicleStatus.DISMISSED;
  }

  private void validateShape() {
    if (requiresSingleUnit(combinationType) && secondaryUnitId != null) {
      throw new InvalidVehicleCombinationException(
          combinationType + " must not have a secondary unit.");
    }

    if (!requiresSingleUnit(combinationType) && secondaryUnitId == null) {
      throw new InvalidVehicleCombinationException(combinationType + " requires a secondary unit.");
    }
  }

  private static void validateUnitTypes(
      VehicleCombinationType combinationType, VehicleUnit primaryUnit, VehicleUnit secondaryUnit) {
    VehicleValidation.requireNonNull(combinationType, "combinationType");

    switch (combinationType) {
      case SINGLE_VEHICLE -> validateSingleVehicle(primaryUnit, secondaryUnit);
      case ARTICULATED_VEHICLE -> validateArticulatedVehicle(primaryUnit, secondaryUnit);
      case ROAD_TRAIN -> validateRoadTrain(primaryUnit, secondaryUnit);
      case VAN_WITH_TRAILER -> validateVanWithTrailer(primaryUnit, secondaryUnit);
      case WAREHOUSE_UNIT -> validateWarehouseUnit(primaryUnit, secondaryUnit);
      case SPECIAL_COMBINATION -> validateSpecialCombination(primaryUnit, secondaryUnit);
    }
  }

  private static void validateSingleVehicle(VehicleUnit primaryUnit, VehicleUnit secondaryUnit) {
    if (secondaryUnit != null) {
      throw new InvalidVehicleCombinationException(
          "Single vehicle combinations cannot have a secondary unit.");
    }

    if (primaryUnit.unitType() == VehicleUnitType.SEMI_TRAILER
        || primaryUnit.unitType() == VehicleUnitType.DRAWBAR_TRAILER
        || primaryUnit.unitType() == VehicleUnitType.CENTER_AXLE_TRAILER) {
      throw new InvalidVehicleCombinationException("Trailers cannot be used as single vehicles.");
    }
  }

  private static void validateArticulatedVehicle(
      VehicleUnit primaryUnit, VehicleUnit secondaryUnit) {
    requireSecondaryUnit(secondaryUnit, "Articulated vehicles require a semi-trailer.");

    if (primaryUnit.unitType() != VehicleUnitType.TRACTOR_UNIT) {
      throw new InvalidVehicleCombinationException(
          "Articulated vehicles require a tractor unit as primary unit.");
    }

    if (secondaryUnit.unitType() != VehicleUnitType.SEMI_TRAILER) {
      throw new InvalidVehicleCombinationException(
          "Articulated vehicles require a semi-trailer as secondary unit.");
    }

    if (!primaryUnit.canTow() || !secondaryUnit.canBeTowed()) {
      throw new InvalidVehicleCombinationException(
          "Articulated vehicle units are not coupling-compatible.");
    }
  }

  private static void validateRoadTrain(VehicleUnit primaryUnit, VehicleUnit secondaryUnit) {
    requireSecondaryUnit(secondaryUnit, "Road trains require a trailer.");

    if (primaryUnit.unitType() != VehicleUnitType.RIGID_TRUCK) {
      throw new InvalidVehicleCombinationException(
          "Road trains require a rigid truck as primary unit.");
    }

    if (secondaryUnit.unitType() != VehicleUnitType.DRAWBAR_TRAILER
        && secondaryUnit.unitType() != VehicleUnitType.CENTER_AXLE_TRAILER) {
      throw new InvalidVehicleCombinationException(
          "Road trains require a drawbar or center-axle trailer as secondary unit.");
    }

    if (!primaryUnit.canTow() || !secondaryUnit.canBeTowed()) {
      throw new InvalidVehicleCombinationException("Road train units are not coupling-compatible.");
    }
  }

  private static void validateVanWithTrailer(VehicleUnit primaryUnit, VehicleUnit secondaryUnit) {
    requireSecondaryUnit(secondaryUnit, "Van with trailer combinations require a trailer.");

    if (primaryUnit.unitType() != VehicleUnitType.VAN) {
      throw new InvalidVehicleCombinationException(
          "Van with trailer combinations require a van as primary unit.");
    }

    if (secondaryUnit.unitType() != VehicleUnitType.DRAWBAR_TRAILER
        && secondaryUnit.unitType() != VehicleUnitType.CENTER_AXLE_TRAILER) {
      throw new InvalidVehicleCombinationException(
          "Van with trailer combinations require a drawbar or center-axle trailer.");
    }

    if (!primaryUnit.canTow() || !secondaryUnit.canBeTowed()) {
      throw new InvalidVehicleCombinationException(
          "Van and trailer units are not coupling-compatible.");
    }
  }

  private static void validateWarehouseUnit(VehicleUnit primaryUnit, VehicleUnit secondaryUnit) {
    if (secondaryUnit != null) {
      throw new InvalidVehicleCombinationException("Warehouse units cannot have a secondary unit.");
    }

    if (primaryUnit.unitType() != VehicleUnitType.WAREHOUSE_EQUIPMENT) {
      throw new InvalidVehicleCombinationException(
          "Warehouse unit combinations require warehouse equipment.");
    }
  }

  private static void validateSpecialCombination(
      VehicleUnit primaryUnit, VehicleUnit secondaryUnit) {
    requireSecondaryUnit(secondaryUnit, "Special combinations require a secondary unit.");

    if (!primaryUnit.canTow() || !secondaryUnit.canBeTowed()) {
      throw new InvalidVehicleCombinationException(
          "Special combination units are not coupling-compatible.");
    }
  }

  private static void requireSecondaryUnit(VehicleUnit secondaryUnit, String message) {
    if (secondaryUnit == null) {
      throw new InvalidVehicleCombinationException(message);
    }
  }

  private static boolean requiresSingleUnit(VehicleCombinationType combinationType) {
    return combinationType == VehicleCombinationType.SINGLE_VEHICLE
        || combinationType == VehicleCombinationType.WAREHOUSE_UNIT;
  }

  private static Set<VehicleCapability> combineCapabilities(
      VehicleUnit primaryUnit, VehicleUnit secondaryUnit) {
    var capabilities = new HashSet<>(primaryUnit.capabilities());
    if (secondaryUnit != null) {
      capabilities.addAll(secondaryUnit.capabilities());
    }

    return Set.copyOf(capabilities);
  }

  private static Set<VehicleOperationalRole> combineOperationalRoles(
      VehicleUnit primaryUnit, VehicleUnit secondaryUnit) {
    var roles = new HashSet<>(primaryUnit.operationalRoles());
    if (secondaryUnit != null) {
      roles.addAll(secondaryUnit.operationalRoles());
    }

    return Set.copyOf(roles);
  }

  private static Set<VehicleCapability> validateCapabilities(Set<VehicleCapability> capabilities) {
    if (capabilities == null) {
      return Set.of();
    }

    VehicleValidation.requireNoNullElements(capabilities, "combinedCapabilities");
    return Set.copyOf(capabilities);
  }

  private static Set<VehicleOperationalRole> validateOperationalRoles(
      Set<VehicleOperationalRole> operationalRoles) {
    if (operationalRoles == null) {
      return Set.of();
    }

    VehicleValidation.requireNoNullElements(operationalRoles, "operationalRoles");
    return Set.copyOf(operationalRoles);
  }
}
