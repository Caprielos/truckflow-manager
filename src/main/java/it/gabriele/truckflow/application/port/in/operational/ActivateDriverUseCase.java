package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.ActivateDriverCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DriverResult;

/** Inbound port for the activate driver use case. */
public interface ActivateDriverUseCase extends UseCase<ActivateDriverCommand, DriverResult> {}
