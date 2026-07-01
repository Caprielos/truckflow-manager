package it.gabriele.truckflow.application.port.in.vehicles;

import it.gabriele.truckflow.application.command.vehicles.RegisterVehicleCombinationCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.vehicles.VehicleCombinationResult;

/** Inbound port for registering vehicle combinations. */
public interface RegisterVehicleCombinationUseCase
    extends UseCase<RegisterVehicleCombinationCommand, VehicleCombinationResult> {}
