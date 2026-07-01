package it.gabriele.truckflow.application.port.in.vehicles;

import it.gabriele.truckflow.application.command.vehicles.MarkVehicleUnitOutOfServiceCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;

/** Inbound port for marking vehicle units out of service. */
public interface MarkVehicleUnitOutOfServiceUseCase
    extends UseCase<MarkVehicleUnitOutOfServiceCommand, VehicleUnitResult> {}
