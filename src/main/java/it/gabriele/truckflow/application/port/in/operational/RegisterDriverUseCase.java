package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.RegisterDriverCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DriverResult;

/** Inbound port for the register driver use case. */
public interface RegisterDriverUseCase extends UseCase<RegisterDriverCommand, DriverResult> {}
