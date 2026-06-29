package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.shipment.Shipment;

/** Contiene regole di dominio relative alle combinazioni veicolari. */
public final class VehicleCombinationRules {

  private VehicleCombinationRules() {}

  public static boolean hasEnoughWeightCapacity(
      VehicleCombination vehicleCombination, CargoLoad cargoLoad) {
    validateVehicleCombination(vehicleCombination);
    validateCargoLoad(cargoLoad);

    return vehicleCombination.canCarryWeight(cargoLoad.calculateTotalWeight());
  }

  public static boolean hasEnoughVolumeCapacity(
      VehicleCombination vehicleCombination, CargoLoad cargoLoad) {
    validateVehicleCombination(vehicleCombination);
    validateCargoLoad(cargoLoad);

    return cargoLoad
        .calculateTotalVolume()
        .isLessThanOrEqualTo(vehicleCombination.calculateCargoSpaceVolume());
  }

  public static boolean hasEnoughSpaceForEveryItem(
      VehicleCombination vehicleCombination, CargoLoad cargoLoad) {
    validateVehicleCombination(vehicleCombination);
    validateCargoLoad(cargoLoad);

    return cargoLoad.allItemsFitInside(vehicleCombination.getCargoSpaceDimension());
  }

  public static boolean supportsRequiredTemperature(
      VehicleCombination vehicleCombination, CargoLoad cargoLoad) {
    validateVehicleCombination(vehicleCombination);
    validateCargoLoad(cargoLoad);

    if (!cargoLoad.requiresTemperatureControl()) {
      return true;
    }

    return vehicleCombination.supportsTemperatureControl();
  }

  public static boolean canPhysicallyCarry(
      VehicleCombination vehicleCombination, CargoLoad cargoLoad) {
    validateVehicleCombination(vehicleCombination);
    validateCargoLoad(cargoLoad);

    return hasEnoughWeightCapacity(vehicleCombination, cargoLoad)
        && hasEnoughVolumeCapacity(vehicleCombination, cargoLoad)
        && hasEnoughSpaceForEveryItem(vehicleCombination, cargoLoad)
        && supportsRequiredTemperature(vehicleCombination, cargoLoad);
  }

  public static boolean canBeAssignedToCargoLoad(
      VehicleCombination vehicleCombination, CargoLoad cargoLoad) {
    validateVehicleCombination(vehicleCombination);
    validateCargoLoad(cargoLoad);

    return vehicleCombination.canBeAssigned() && canPhysicallyCarry(vehicleCombination, cargoLoad);
  }

  public static boolean canBeAssignedToShipment(
      VehicleCombination vehicleCombination, Shipment shipment) {
    validateVehicleCombination(vehicleCombination);

    if (shipment == null) {
      throw new IllegalArgumentException("La spedizione è obbligatoria.");
    }

    return canBeAssignedToCargoLoad(vehicleCombination, shipment.getCargoLoad());
  }

  private static void validateVehicleCombination(VehicleCombination vehicleCombination) {
    if (vehicleCombination == null) {
      throw new IllegalArgumentException("La combinazione veicolare è obbligatoria.");
    }
  }

  private static void validateCargoLoad(CargoLoad cargoLoad) {
    if (cargoLoad == null) {
      throw new IllegalArgumentException("Il carico è obbligatorio.");
    }
  }
}
