package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.SuspendMechanicCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.MechanicResult;

/** Inbound port for the suspend mechanic use case. */
public interface SuspendMechanicUseCase extends UseCase<SuspendMechanicCommand, MechanicResult> {}
