package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.MarkNotEligibleManagerCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.ManagerResult;

/** Inbound port for the markNotEligible manager use case. */
public interface MarkNotEligibleManagerUseCase
    extends UseCase<MarkNotEligibleManagerCommand, ManagerResult> {}
