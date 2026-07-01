package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.FindDriverCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DriverResult;

/** Inbound port for the find driver use case. */
public interface FindDriverUseCase extends UseCase<FindDriverCommand, DriverResult> {}
