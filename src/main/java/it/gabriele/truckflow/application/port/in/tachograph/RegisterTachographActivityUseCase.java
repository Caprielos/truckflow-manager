package it.gabriele.truckflow.application.port.in.tachograph;

import it.gabriele.truckflow.domain.tachograph.TachographActivity;

public interface RegisterTachographActivityUseCase {
  TachographActivity handle(Command command);

  record Command(TachographActivity activity) {}
}
