package it.gabriele.truckflow.application.port.in.operational;

import it.gabriele.truckflow.application.command.operational.FindManagerCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.operational.ManagerResult;

/** Inbound port for the find manager use case. */
public interface FindManagerUseCase extends UseCase<FindManagerCommand, ManagerResult> {}
