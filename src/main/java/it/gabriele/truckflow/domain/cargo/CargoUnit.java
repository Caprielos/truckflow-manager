package it.gabriele.truckflow.domain.cargo;

import java.util.Set;

public final class CargoUnit {

  private final CargoId id;
  private final CargoCode code;
  private String name;
  private String description;
  private CargoType type;
  private Set<CargoCategory> categories;
  private CargoDimensions dimensions;
  private CargoWeights weights;
  private CargoPackaging packaging;
  private CargoTemperature temperature;
  private CargoHazard hazard;
  private CargoRegulatory regulatory;
  private CargoProperties properties;
  private CargoCompatibilityRequirement compatibilityRequirement;
  private CargoStatus status;
  private String notes;

  public CargoUnit(
      CargoId id,
      CargoCode code,
      String name,
      String description,
      CargoType type,
      Set<CargoCategory> categories,
      CargoDimensions dimensions,
      CargoWeights weights,
      CargoPackaging packaging,
      CargoTemperature temperature,
      CargoHazard hazard,
      CargoRegulatory regulatory,
      CargoProperties properties,
      CargoCompatibilityRequirement compatibilityRequirement,
      CargoStatus status,
      String notes) {
    this.id = id == null ? CargoId.random() : id;
    this.code = CargoValidation.requireNonNull(code, "code");
    this.name = CargoValidation.requireText(name, "name");
    this.description = CargoValidation.normalize(description);
    this.type = CargoValidation.requireNonNull(type, "type");
    this.categories = validateCategories(categories);
    this.dimensions = dimensions == null ? CargoDimensions.empty() : dimensions;
    this.weights = weights == null ? CargoWeights.empty() : weights;
    this.packaging = packaging == null ? CargoPackaging.loose() : packaging;
    this.temperature = temperature == null ? CargoTemperature.uncontrolled() : temperature;
    this.hazard = hazard == null ? CargoHazard.none() : hazard;
    this.regulatory = regulatory == null ? CargoRegulatory.none() : regulatory;
    this.properties = properties == null ? CargoProperties.standard() : properties;
    this.compatibilityRequirement =
        compatibilityRequirement == null
            ? CargoCompatibilityRequirement.none()
            : compatibilityRequirement;
    this.status = CargoValidation.requireNonNull(status, "status");
    this.notes = CargoValidation.normalize(notes);

    validateConsistency();
  }

  public CargoId id() {
    return id;
  }

  public CargoCode code() {
    return code;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public CargoType type() {
    return type;
  }

  public Set<CargoCategory> categories() {
    return Set.copyOf(categories);
  }

  public CargoDimensions dimensions() {
    return dimensions;
  }

  public CargoWeights weights() {
    return weights;
  }

  public CargoPackaging packaging() {
    return packaging;
  }

  public CargoTemperature temperature() {
    return temperature;
  }

  public CargoHazard hazard() {
    return hazard;
  }

  public CargoRegulatory regulatory() {
    return regulatory;
  }

  public CargoProperties properties() {
    return properties;
  }

  public CargoCompatibilityRequirement compatibilityRequirement() {
    return compatibilityRequirement;
  }

  public CargoStatus status() {
    return status;
  }

  public String notes() {
    return notes;
  }

  public boolean isActive() {
    return status == CargoStatus.ACTIVE;
  }

  public boolean hasCategory(CargoCategory category) {
    CargoValidation.requireNonNull(category, "category");
    return categories.contains(category);
  }

  public boolean requires(CargoTransportRequirement requirement) {
    return compatibilityRequirement.requires(requirement);
  }

  public boolean isTemperatureControlled() {
    return temperature.controlled();
  }

  public boolean isHazardous() {
    return properties.dangerous() || hazard.hasAdrInformation() || regulatory.adrRequired();
  }

  public void rename(String name, String description) {
    this.name = CargoValidation.requireText(name, "name");
    this.description = CargoValidation.normalize(description);
  }

  public void replaceCategories(Set<CargoCategory> categories) {
    this.categories = validateCategories(categories);
    validateConsistency();
  }

  public void replaceDimensions(CargoDimensions dimensions) {
    this.dimensions = dimensions == null ? CargoDimensions.empty() : dimensions;
  }

  public void replaceWeights(CargoWeights weights) {
    this.weights = weights == null ? CargoWeights.empty() : weights;
  }

  public void replacePackaging(CargoPackaging packaging) {
    this.packaging = packaging == null ? CargoPackaging.loose() : packaging;
  }

  public void replaceTemperature(CargoTemperature temperature) {
    this.temperature = temperature == null ? CargoTemperature.uncontrolled() : temperature;
    validateConsistency();
  }

  public void replaceHazard(CargoHazard hazard) {
    this.hazard = hazard == null ? CargoHazard.none() : hazard;
    validateConsistency();
  }

  public void replaceRegulatory(CargoRegulatory regulatory) {
    this.regulatory = regulatory == null ? CargoRegulatory.none() : regulatory;
    validateConsistency();
  }

  public void replaceProperties(CargoProperties properties) {
    this.properties = properties == null ? CargoProperties.standard() : properties;
    validateConsistency();
  }

  public void replaceCompatibilityRequirement(
      CargoCompatibilityRequirement compatibilityRequirement) {
    this.compatibilityRequirement =
        compatibilityRequirement == null
            ? CargoCompatibilityRequirement.none()
            : compatibilityRequirement;
    validateConsistency();
  }

  public void activate() {
    status = CargoStatus.ACTIVE;
  }

  public void suspend() {
    status = CargoStatus.SUSPENDED;
  }

  public void archive() {
    status = CargoStatus.ARCHIVED;
  }

  public void discontinue() {
    status = CargoStatus.DISCONTINUED;
  }

  private static Set<CargoCategory> validateCategories(Set<CargoCategory> categories) {
    if (categories == null || categories.isEmpty()) {
      throw new IllegalArgumentException("categories are required.");
    }

    CargoValidation.requireNoNullElements(categories, "categories");
    return Set.copyOf(categories);
  }

  private void validateConsistency() {
    if (regulatory.adrRequired()
        && !compatibilityRequirement.requires(CargoTransportRequirement.ADR_VEHICLE_REQUIRED)) {
      throw new IllegalArgumentException(
          "ADR cargo must require ADR vehicle compatibility requirement.");
    }

    if (regulatory.atpRequired()
        && !compatibilityRequirement.requires(
            CargoTransportRequirement.ATP_CERTIFICATION_REQUIRED)) {
      throw new IllegalArgumentException(
          "ATP cargo must require ATP certification compatibility requirement.");
    }

    if (temperature.controlled()
        && !compatibilityRequirement.requires(
            CargoTransportRequirement.TEMPERATURE_CONTROL_REQUIRED)) {
      throw new IllegalArgumentException(
          "Temperature controlled cargo must require temperature control requirement.");
    }

    if (properties.requiresSeparation()
        && !compatibilityRequirement.requires(CargoTransportRequirement.SEPARATION_REQUIRED)) {
      throw new IllegalArgumentException(
          "Cargo requiring separation must declare separation transport requirement.");
    }
  }
}
