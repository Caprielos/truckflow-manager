package it.gabriele.truckflow.application.usecase.quality;

import it.gabriele.truckflow.application.port.in.quality.AssignCorrectiveActionUseCase;
import it.gabriele.truckflow.application.port.out.quality.QualityEventRepository;
import it.gabriele.truckflow.domain.quality.QualityEvent;
import java.util.Objects;

/** Caso d'uso: assegnare azione correttiva a evento qualità. */
public final class DefaultAssignCorrectiveActionUseCase implements AssignCorrectiveActionUseCase {

  private final QualityEventRepository qualityEventRepository;

  public DefaultAssignCorrectiveActionUseCase(QualityEventRepository qualityEventRepository) {
    this.qualityEventRepository =
        Objects.requireNonNull(qualityEventRepository, "Il repository qualità è obbligatorio.");
  }

  @Override
  public QualityEvent handle(Command command) {
    Objects.requireNonNull(command, "Il comando azione correttiva è obbligatorio.");
    QualityEvent event = qualityEventRepository.getRequired(command.eventCode(), "Evento qualità");
    QualityEvent updated = event.assignCorrectiveAction(command.correctiveAction());
    qualityEventRepository.save(updated);
    return updated;
  }
}
