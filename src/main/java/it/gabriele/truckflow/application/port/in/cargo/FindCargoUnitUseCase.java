package it.gabriele.truckflow.application.port.in.cargo;

import it.gabriele.truckflow.application.command.cargo.FindCargoUnitCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.cargo.CargoUnitResult;

/** Inbound port for finding a cargo unit. */
public interface FindCargoUnitUseCase extends UseCase<FindCargoUnitCommand, CargoUnitResult> {}
