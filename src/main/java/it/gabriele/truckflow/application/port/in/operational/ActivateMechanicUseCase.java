package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.ActivateMechanicCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.MechanicResult;

/** Inbound port for the activate mechanic use case. */
public interface ActivateMechanicUseCase extends UseCase<ActivateMechanicCommand, MechanicResult> {}
