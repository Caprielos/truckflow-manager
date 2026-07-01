package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.FindDispatcherCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;

/** Inbound port for the find dispatcher use case. */
public interface FindDispatcherUseCase extends UseCase<FindDispatcherCommand, DispatcherResult> {}
