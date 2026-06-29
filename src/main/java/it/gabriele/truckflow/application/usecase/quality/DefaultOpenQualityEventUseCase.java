package it.gabriele.truckflow.application.usecase.quality;

import it.gabriele.truckflow.application.port.in.quality.OpenQualityEventUseCase;
import it.gabriele.truckflow.application.port.out.QualityEventRepository;
import it.gabriele.truckflow.domain.quality.QualityEvent;
import java.util.Objects;

/** Caso d'uso: aprire evento qualità, reclamo o non conformità. */
public final class DefaultOpenQualityEventUseCase implements OpenQualityEventUseCase {

  private final QualityEventRepository qualityEventRepository;

  public DefaultOpenQualityEventUseCase(QualityEventRepository qualityEventRepository) {
    this.qualityEventRepository =
        Objects.requireNonNull(qualityEventRepository, "Il repository qualità è obbligatorio.");
  }

  @Override
  public QualityEvent handle(Command command) {
    Objects.requireNonNull(command, "Il comando evento qualità è obbligatorio.");
    QualityEvent event =
        Objects.requireNonNull(command.event(), "L'evento qualità è obbligatorio.");
    qualityEventRepository.save(event);
    return event;
  }
}
