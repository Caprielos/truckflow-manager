package it.gabriele.truckflow.domain.vehicles;

import java.util.HashSet;
import java.util.Set;

public final class VehicleUnit {

  private final VehicleUnitId id;
  private final FleetCode fleetCode;
  private final String licensePlate;
  private final String vin;
  private final VehicleUnitType unitType;
  private final VehicleBodyType bodyType;
  private final PowerSource powerSource;
  private VehicleTechnicalSpecification technicalSpecification;
  private VehicleBodyProfile bodyProfile;
  private Set<VehicleCapability> capabilities;
  private Set<VehicleOperationalRole> operationalRoles;
  private CouplingProfile couplingProfile;
  private VehicleStatus status;
  private String notes;

  public VehicleUnit(
      VehicleUnitId id,
      FleetCode fleetCode,
      String licensePlate,
      String vin,
      VehicleUnitType unitType,
      VehicleBodyType bodyType,
      PowerSource powerSource,
      VehicleTechnicalSpecification technicalSpecification,
      VehicleBodyProfile bodyProfile,
      Set<VehicleCapability> capabilities,
      Set<VehicleOperationalRole> operationalRoles,
      CouplingProfile couplingProfile,
      VehicleStatus status,
      String notes) {
    this.id = id == null ? VehicleUnitId.random() : id;
    this.fleetCode = VehicleValidation.requireNonNull(fleetCode, "fleetCode");
    this.licensePlate = normalizePlate(licensePlate);
    this.vin = normalizeVin(vin);
    this.unitType = VehicleValidation.requireNonNull(unitType, "unitType");
    this.bodyType = VehicleValidation.requireNonNull(bodyType, "bodyType");
    this.powerSource = VehicleValidation.requireNonNull(powerSource, "powerSource");
    this.technicalSpecification =
        VehicleValidation.requireNonNull(technicalSpecification, "technicalSpecification");
    this.bodyProfile = bodyProfile;
    this.capabilities = validateCapabilities(capabilities);
    this.operationalRoles = validateOperationalRoles(operationalRoles);
    this.couplingProfile = couplingProfile == null ? CouplingProfile.none() : couplingProfile;
    this.status = VehicleValidation.requireNonNull(status, "status");
    this.notes = VehicleValidation.normalize(notes);

    validateConsistency();
  }

  public VehicleUnitId id() {
    return id;
  }

  public FleetCode fleetCode() {
    return fleetCode;
  }

  public String licensePlate() {
    return licensePlate;
  }

  public String vin() {
    return vin;
  }

  public VehicleUnitType unitType() {
    return unitType;
  }

  public VehicleBodyType bodyType() {
    return bodyType;
  }

  public PowerSource powerSource() {
    return powerSource;
  }

  public VehicleTechnicalSpecification technicalSpecification() {
    return technicalSpecification;
  }

  public VehicleBodyProfile bodyProfile() {
    return bodyProfile;
  }

  public Set<VehicleCapability> capabilities() {
    return Set.copyOf(capabilities);
  }

  public Set<VehicleOperationalRole> operationalRoles() {
    return Set.copyOf(operationalRoles);
  }

  public CouplingProfile couplingProfile() {
    return couplingProfile;
  }

  public VehicleStatus status() {
    return status;
  }

  public String notes() {
    return notes;
  }

  public boolean isActive() {
    return status == VehicleStatus.ACTIVE;
  }

  public boolean isOutOfService() {
    return status == VehicleStatus.OUT_OF_SERVICE;
  }

  public boolean hasCapability(VehicleCapability capability) {
    VehicleValidation.requireNonNull(capability, "capability");
    return capabilities.contains(capability);
  }

  public boolean hasOperationalRole(VehicleOperationalRole role) {
    VehicleValidation.requireNonNull(role, "role");
    return operationalRoles.contains(role);
  }

  public boolean canTow() {
    return couplingProfile.canTow();
  }

  public boolean canBeTowed() {
    return couplingProfile.canBeTowed();
  }

  public boolean isTrailer() {
    return isTrailerUnitType(unitType);
  }

  public void replaceTechnicalSpecification(
      VehicleTechnicalSpecification technicalSpecification, String notes) {
    this.technicalSpecification =
        VehicleValidation.requireNonNull(technicalSpecification, "technicalSpecification");
    this.notes = VehicleValidation.normalize(notes);
    validateConsistency();
  }

  public void replaceBodyProfile(VehicleBodyProfile bodyProfile) {
    this.bodyProfile = bodyProfile;
    validateConsistency();
  }

  public void replaceCouplingProfile(CouplingProfile couplingProfile) {
    this.couplingProfile = couplingProfile == null ? CouplingProfile.none() : couplingProfile;
    validateConsistency();
  }

  public void addCapability(VehicleCapability capability) {
    VehicleValidation.requireNonNull(capability, "capability");
    var updatedCapabilities = new HashSet<>(capabilities);
    updatedCapabilities.add(capability);
    capabilities = validateCapabilities(updatedCapabilities);
  }

  public void removeCapability(VehicleCapability capability) {
    VehicleValidation.requireNonNull(capability, "capability");
    var updatedCapabilities = new HashSet<>(capabilities);
    updatedCapabilities.remove(capability);
    capabilities = validateCapabilities(updatedCapabilities);
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

  private void validateConsistency() {
    if (isTrailerUnitType(unitType) && powerSource != PowerSource.NONE) {
      throw new IllegalArgumentException("Trailers must have power source NONE.");
    }

    if (unitType == VehicleUnitType.TRACTOR_UNIT && bodyType != VehicleBodyType.NONE) {
      throw new IllegalArgumentException("Tractor units must have body type NONE.");
    }

    if (unitType == VehicleUnitType.TRACTOR_UNIT && !couplingProfile.canTow()) {
      throw new IllegalArgumentException("Tractor units must be able to tow.");
    }

    if (unitType == VehicleUnitType.SEMI_TRAILER && !couplingProfile.canBeTowed()) {
      throw new IllegalArgumentException("Semi-trailers must be able to be towed.");
    }

    if (isDrawbarTrailer(unitType) && !couplingProfile.canBeTowed()) {
      throw new IllegalArgumentException("Drawbar trailers must be able to be towed.");
    }

    if (bodyProfile != null && bodyProfile.bodyType() != bodyType) {
      throw new IllegalArgumentException("Body profile must match vehicle body type.");
    }
  }

  private static boolean isTrailerUnitType(VehicleUnitType unitType) {
    return unitType == VehicleUnitType.SEMI_TRAILER
        || unitType == VehicleUnitType.DRAWBAR_TRAILER
        || unitType == VehicleUnitType.CENTER_AXLE_TRAILER;
  }

  private static boolean isDrawbarTrailer(VehicleUnitType unitType) {
    return unitType == VehicleUnitType.DRAWBAR_TRAILER
        || unitType == VehicleUnitType.CENTER_AXLE_TRAILER;
  }

  private static Set<VehicleCapability> validateCapabilities(Set<VehicleCapability> capabilities) {
    if (capabilities == null) {
      return Set.of();
    }

    VehicleValidation.requireNoNullElements(capabilities, "capabilities");
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

  private static String normalizePlate(String value) {
    return VehicleValidation.normalize(value).toUpperCase().replace(" ", "");
  }

  private static String normalizeVin(String value) {
    return VehicleValidation.normalize(value).toUpperCase().replace(" ", "");
  }
}
