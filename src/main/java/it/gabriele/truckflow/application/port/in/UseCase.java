package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.result.ApplicationResult;

/** Generic contract for application use cases. */
public interface UseCase<C extends ApplicationCommand, R extends ApplicationResult> {

  R execute(C command);
}
