package it.gabriele.truckflow.domain.shipments;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public final class Shipment {

  private final ShipmentId id;
  private final ShipmentCode code;
  private String name;
  private String description;
  private ShipmentStatus status;
  private ShipmentPriority priority;
  private ShipmentServiceLevel serviceLevel;
  private List<ShipmentItem> items;
  private List<ShipmentLeg> legs;
  private ShipmentProperties properties;
  private ShipmentTemperature temperature;
  private ShipmentRequirementSet requirementSet;
  private ShipmentMetrics metrics;
  private ShipmentReferences references;
  private ShipmentNotes notes;
  private String generalNotes;

  public Shipment(
      ShipmentId id,
      ShipmentCode code,
      String name,
      String description,
      ShipmentStatus status,
      ShipmentPriority priority,
      ShipmentServiceLevel serviceLevel,
      List<ShipmentItem> items,
      List<ShipmentLeg> legs,
      ShipmentProperties properties,
      ShipmentTemperature temperature,
      ShipmentRequirementSet requirementSet,
      ShipmentMetrics metrics,
      ShipmentReferences references,
      ShipmentNotes notes,
      String generalNotes) {
    this.id = id == null ? ShipmentId.random() : id;
    this.code = ShipmentValidation.requireNonNull(code, "code");
    this.name = ShipmentValidation.requireText(name, "name");
    this.description = ShipmentValidation.normalize(description);
    this.status = ShipmentValidation.requireNonNull(status, "status");
    this.priority = ShipmentValidation.requireNonNull(priority, "priority");
    this.serviceLevel = ShipmentValidation.requireNonNull(serviceLevel, "serviceLevel");
    this.items = validateItems(items, status);
    this.legs = validateLegs(legs, status);
    this.properties = properties == null ? ShipmentProperties.standard() : properties;
    this.temperature = temperature == null ? ShipmentTemperature.uncontrolled() : temperature;
    this.requirementSet = requirementSet == null ? ShipmentRequirementSet.none() : requirementSet;
    this.metrics = metrics == null ? ShipmentMetrics.empty() : metrics;
    this.references = references == null ? ShipmentReferences.empty() : references;
    this.notes = notes == null ? ShipmentNotes.empty() : notes;
    this.generalNotes = ShipmentValidation.normalize(generalNotes);

    validateConsistency();
  }

  public ShipmentId id() {
    return id;
  }

  public ShipmentCode code() {
    return code;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public ShipmentStatus status() {
    return status;
  }

  public ShipmentPriority priority() {
    return priority;
  }

  public ShipmentServiceLevel serviceLevel() {
    return serviceLevel;
  }

  public List<ShipmentItem> items() {
    return List.copyOf(items);
  }

  public List<ShipmentLeg> legs() {
    return List.copyOf(legs);
  }

  public ShipmentProperties properties() {
    return properties;
  }

  public ShipmentTemperature temperature() {
    return temperature;
  }

  public ShipmentRequirementSet requirementSet() {
    return requirementSet;
  }

  public ShipmentMetrics metrics() {
    return metrics;
  }

  public ShipmentReferences references() {
    return references;
  }

  public ShipmentNotes notes() {
    return notes;
  }

  public String generalNotes() {
    return generalNotes;
  }

  public boolean isConfirmed() {
    return status == ShipmentStatus.CONFIRMED;
  }

  public int itemCount() {
    return items.size();
  }

  public int legCount() {
    return legs.size();
  }

  public boolean requires(ShipmentTransportRequirement requirement) {
    return requirementSet.requires(requirement);
  }

  public boolean isTemperatureControlled() {
    return temperature.controlled();
  }

  public boolean isContinuous() {
    if (legs.size() < 2) {
      return true;
    }

    for (int index = 0; index < legs.size() - 1; index++) {
      if (!legs.get(index).connectsTo(legs.get(index + 1))) {
        return false;
      }
    }

    return true;
  }

  public void rename(String name, String description) {
    this.name = ShipmentValidation.requireText(name, "name");
    this.description = ShipmentValidation.normalize(description);
  }

  public void changePriority(ShipmentPriority priority) {
    this.priority = ShipmentValidation.requireNonNull(priority, "priority");
  }

  public void changeServiceLevel(ShipmentServiceLevel serviceLevel) {
    this.serviceLevel = ShipmentValidation.requireNonNull(serviceLevel, "serviceLevel");
  }

  public void replaceItems(List<ShipmentItem> items) {
    this.items = validateItems(items, status);
  }

  public void replaceLegs(List<ShipmentLeg> legs) {
    this.legs = validateLegs(legs, status);
  }

  public void replaceProperties(ShipmentProperties properties) {
    this.properties = properties == null ? ShipmentProperties.standard() : properties;
    validateConsistency();
  }

  public void replaceTemperature(ShipmentTemperature temperature) {
    this.temperature = temperature == null ? ShipmentTemperature.uncontrolled() : temperature;
    validateConsistency();
  }

  public void replaceRequirementSet(ShipmentRequirementSet requirementSet) {
    this.requirementSet = requirementSet == null ? ShipmentRequirementSet.none() : requirementSet;
    validateConsistency();
  }

  public void replaceMetrics(ShipmentMetrics metrics) {
    this.metrics = metrics == null ? ShipmentMetrics.empty() : metrics;
  }

  public void replaceReferences(ShipmentReferences references) {
    this.references = references == null ? ShipmentReferences.empty() : references;
  }

  public void replaceNotes(ShipmentNotes notes) {
    this.notes = notes == null ? ShipmentNotes.empty() : notes;
  }

  public void updateGeneralNotes(String generalNotes) {
    this.generalNotes = ShipmentValidation.normalize(generalNotes);
  }

  public void register() {
    status = ShipmentStatus.REGISTERED;
  }

  public void confirm() {
    status = ShipmentStatus.CONFIRMED;
    items = validateItems(items, status);
    legs = validateLegs(legs, status);
    validateConsistency();
  }

  public void suspend() {
    status = ShipmentStatus.SUSPENDED;
  }

  public void cancel() {
    status = ShipmentStatus.CANCELLED;
  }

  public void archive() {
    status = ShipmentStatus.ARCHIVED;
  }

  private static List<ShipmentItem> validateItems(List<ShipmentItem> items, ShipmentStatus status) {
    if (items == null) {
      items = List.of();
    }

    ShipmentValidation.requireNoNullElements(items, "items");

    if (status == ShipmentStatus.CONFIRMED && items.isEmpty()) {
      throw new IllegalArgumentException("Confirmed shipments must have at least one item.");
    }

    return List.copyOf(items);
  }

  private static List<ShipmentLeg> validateLegs(List<ShipmentLeg> legs, ShipmentStatus status) {
    if (legs == null) {
      legs = List.of();
    }

    ShipmentValidation.requireNoNullElements(legs, "legs");

    if (status == ShipmentStatus.CONFIRMED && legs.isEmpty()) {
      throw new IllegalArgumentException("Confirmed shipments must have at least one leg.");
    }

    var sequenceNumbers = new HashSet<Integer>();
    for (ShipmentLeg leg : legs) {
      if (!sequenceNumbers.add(leg.sequenceNumber())) {
        throw new IllegalArgumentException("Shipment leg sequence numbers must be unique.");
      }
    }

    return legs.stream().sorted(Comparator.comparingInt(ShipmentLeg::sequenceNumber)).toList();
  }

  private void validateConsistency() {
    if (temperature.controlled()
        && !requirementSet.requires(ShipmentTransportRequirement.TEMPERATURE_CONTROL_REQUIRED)) {
      throw new IllegalArgumentException(
          "Temperature controlled shipments must declare TEMPERATURE_CONTROL_REQUIRED.");
    }

    if (properties.requiresSeparation()
        && !requirementSet.requires(ShipmentTransportRequirement.SEPARATION_REQUIRED)) {
      throw new IllegalArgumentException(
          "Shipments requiring separation must declare SEPARATION_REQUIRED.");
    }
  }
}
