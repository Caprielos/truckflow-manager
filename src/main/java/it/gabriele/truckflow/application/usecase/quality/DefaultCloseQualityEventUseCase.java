package it.gabriele.truckflow.application.usecase.quality;

import it.gabriele.truckflow.application.port.in.CloseQualityEventUseCase;
import it.gabriele.truckflow.application.port.out.QualityEventRepository;
import it.gabriele.truckflow.domain.quality.QualityEvent;
import java.util.Objects;

/** Caso d'uso: chiudere evento qualità dopo gestione e azioni correttive. */
public final class DefaultCloseQualityEventUseCase implements CloseQualityEventUseCase {

  private final QualityEventRepository qualityEventRepository;

  public DefaultCloseQualityEventUseCase(QualityEventRepository qualityEventRepository) {
    this.qualityEventRepository =
        Objects.requireNonNull(qualityEventRepository, "Il repository qualità è obbligatorio.");
  }

  @Override
  public QualityEvent handle(Command command) {
    Objects.requireNonNull(command, "Il comando chiusura qualità è obbligatorio.");
    QualityEvent event = qualityEventRepository.getRequired(command.eventCode(), "Evento qualità");
    QualityEvent closed = event.close(command.closedAt());
    qualityEventRepository.save(closed);
    return closed;
  }
}
