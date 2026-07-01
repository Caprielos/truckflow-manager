package it.gabriele.truckflow.application.port.in.vehicles;

import it.gabriele.truckflow.application.command.vehicles.SuspendVehicleUnitCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Inbound port for suspending vehicle units. */
public interface SuspendVehicleUnitUseCase
    extends UseCase<SuspendVehicleUnitCommand, VehicleUnitResult> {}
