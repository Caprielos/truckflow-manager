package it.gabriele.truckflow.domain.vehicles.specification;

public record VehicleTechnicalSpecification(
    VehicleDimensions dimensions,
    VehicleLoadSpace loadSpace,
    VehicleWeights weights,
    VehicleAxleSpecification axles,
    VehicleTireSpecification tires,
    VehicleEngineSpecification engine,
    VehicleTransmissionSpecification transmission,
    VehicleChassisSpecification chassis,
    VehicleElectricSpecification electric,
    VehicleCabSpecification cabin) {

  public VehicleTechnicalSpecification {
    dimensions = dimensions == null ? VehicleDimensions.empty() : dimensions;
    loadSpace = loadSpace == null ? VehicleLoadSpace.empty() : loadSpace;
    weights = weights == null ? VehicleWeights.empty() : weights;
    axles = axles == null ? VehicleAxleSpecification.empty() : axles;
    tires = tires == null ? VehicleTireSpecification.empty() : tires;
    engine = engine == null ? VehicleEngineSpecification.empty() : engine;
    transmission = transmission == null ? VehicleTransmissionSpecification.empty() : transmission;
    chassis = chassis == null ? VehicleChassisSpecification.empty() : chassis;
    electric = electric == null ? VehicleElectricSpecification.empty() : electric;
    cabin = cabin == null ? VehicleCabSpecification.empty() : cabin;
  }

  public static VehicleTechnicalSpecification empty() {
    return new VehicleTechnicalSpecification(
        null, null, null, null, null, null, null, null, null, null);
  }
}
