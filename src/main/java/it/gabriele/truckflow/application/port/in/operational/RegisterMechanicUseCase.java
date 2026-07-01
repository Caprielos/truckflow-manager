package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.RegisterMechanicCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.MechanicResult;

/** Inbound port for the register mechanic use case. */
public interface RegisterMechanicUseCase extends UseCase<RegisterMechanicCommand, MechanicResult> {}
