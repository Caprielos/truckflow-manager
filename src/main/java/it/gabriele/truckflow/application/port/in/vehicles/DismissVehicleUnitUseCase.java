package it.gabriele.truckflow.application.port.in.vehicles;

import it.gabriele.truckflow.application.command.vehicles.DismissVehicleUnitCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Inbound port for dismissing vehicle units. */
public interface DismissVehicleUnitUseCase
    extends UseCase<DismissVehicleUnitCommand, VehicleUnitResult> {}
