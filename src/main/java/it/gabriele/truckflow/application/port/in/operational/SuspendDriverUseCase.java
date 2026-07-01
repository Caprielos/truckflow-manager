package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.SuspendDriverCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DriverResult;

/** Inbound port for the suspend driver use case. */
public interface SuspendDriverUseCase extends UseCase<SuspendDriverCommand, DriverResult> {}
