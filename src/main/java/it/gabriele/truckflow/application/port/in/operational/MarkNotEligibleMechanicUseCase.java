package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.MarkNotEligibleMechanicCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.MechanicResult;

/** Inbound port for the markNotEligible mechanic use case. */
public interface MarkNotEligibleMechanicUseCase
    extends UseCase<MarkNotEligibleMechanicCommand, MechanicResult> {}
