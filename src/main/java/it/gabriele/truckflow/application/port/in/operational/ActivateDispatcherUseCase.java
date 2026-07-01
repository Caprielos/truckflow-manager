package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.ActivateDispatcherCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;

/** Inbound port for the activate dispatcher use case. */
public interface ActivateDispatcherUseCase
    extends UseCase<ActivateDispatcherCommand, DispatcherResult> {}
