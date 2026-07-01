package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.MarkNotEligibleDriverCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DriverResult;

/** Inbound port for the markNotEligible driver use case. */
public interface MarkNotEligibleDriverUseCase
    extends UseCase<MarkNotEligibleDriverCommand, DriverResult> {}
