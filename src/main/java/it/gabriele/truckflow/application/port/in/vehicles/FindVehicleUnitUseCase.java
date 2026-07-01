package it.gabriele.truckflow.application.port.in.vehicles;

import it.gabriele.truckflow.application.command.vehicles.FindVehicleUnitCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Inbound port for finding vehicle units. */
public interface FindVehicleUnitUseCase
    extends UseCase<FindVehicleUnitCommand, VehicleUnitResult> {}
