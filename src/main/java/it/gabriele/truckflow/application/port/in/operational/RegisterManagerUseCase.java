package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.RegisterManagerCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.ManagerResult;

/** Inbound port for the register manager use case. */
public interface RegisterManagerUseCase extends UseCase<RegisterManagerCommand, ManagerResult> {}
