package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.SuspendDispatcherCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;

/** Inbound port for the suspend dispatcher use case. */
public interface SuspendDispatcherUseCase
    extends UseCase<SuspendDispatcherCommand, DispatcherResult> {}
