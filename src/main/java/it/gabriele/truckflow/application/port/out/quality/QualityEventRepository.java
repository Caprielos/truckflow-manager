package it.gabriele.truckflow.application.port.out.quality;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.quality.QualityEvent;

/** Repository port per eventi qualità e non conformità. */
public interface QualityEventRepository extends RepositoryPort<QualityEvent> {}
