package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.RegisterDispatcherCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;

/** Inbound port for the register dispatcher use case. */
public interface RegisterDispatcherUseCase
    extends UseCase<RegisterDispatcherCommand, DispatcherResult> {}
