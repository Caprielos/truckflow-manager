package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.MarkNotEligibleDispatcherCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;

/** Inbound port for the markNotEligible dispatcher use case. */
public interface MarkNotEligibleDispatcherUseCase
    extends UseCase<MarkNotEligibleDispatcherCommand, DispatcherResult> {}
