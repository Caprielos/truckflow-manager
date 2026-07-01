package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.FindMechanicCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.MechanicResult;

/** Inbound port for the find mechanic use case. */
public interface FindMechanicUseCase extends UseCase<FindMechanicCommand, MechanicResult> {}
