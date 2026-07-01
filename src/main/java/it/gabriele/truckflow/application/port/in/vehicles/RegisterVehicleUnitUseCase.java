package it.gabriele.truckflow.application.port.in.vehicles;

import it.gabriele.truckflow.application.command.vehicles.RegisterVehicleUnitCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Inbound port for registering vehicle units. */
public interface RegisterVehicleUnitUseCase
    extends UseCase<RegisterVehicleUnitCommand, VehicleUnitResult> {}
