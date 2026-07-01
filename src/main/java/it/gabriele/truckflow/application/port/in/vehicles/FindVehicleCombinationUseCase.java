package it.gabriele.truckflow.application.port.in.vehicles;

import it.gabriele.truckflow.application.command.vehicles.FindVehicleCombinationCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.vehicles.VehicleCombinationResult;

/** Inbound port for finding vehicle combinations. */
public interface FindVehicleCombinationUseCase
    extends UseCase<FindVehicleCombinationCommand, VehicleCombinationResult> {}
