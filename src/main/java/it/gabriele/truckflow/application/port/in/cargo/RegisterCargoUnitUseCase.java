package it.gabriele.truckflow.application.port.in.cargo;

import it.gabriele.truckflow.application.command.cargo.RegisterCargoUnitCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.cargo.CargoUnitResult;

/** Inbound port for registering a cargo unit. */
public interface RegisterCargoUnitUseCase
    extends UseCase<RegisterCargoUnitCommand, CargoUnitResult> {}
