package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.SuspendManagerCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.ManagerResult;

/** Inbound port for the suspend manager use case. */
public interface SuspendManagerUseCase extends UseCase<SuspendManagerCommand, ManagerResult> {}
