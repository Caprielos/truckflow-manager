package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.ActivateManagerCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.ManagerResult;

/** Inbound port for the activate manager use case. */
public interface ActivateManagerUseCase extends UseCase<ActivateManagerCommand, ManagerResult> {}
