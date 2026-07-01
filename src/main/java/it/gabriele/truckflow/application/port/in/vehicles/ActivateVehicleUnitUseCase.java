package it.gabriele.truckflow.application.port.in.vehicles;

import it.gabriele.truckflow.application.command.vehicles.ActivateVehicleUnitCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Inbound port for activating vehicle units. */
public interface ActivateVehicleUnitUseCase
    extends UseCase<ActivateVehicleUnitCommand, VehicleUnitResult> {}
