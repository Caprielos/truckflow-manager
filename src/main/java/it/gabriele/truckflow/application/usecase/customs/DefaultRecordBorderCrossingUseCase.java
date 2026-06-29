package it.gabriele.truckflow.application.usecase.customs;

import it.gabriele.truckflow.application.port.in.customs.RecordBorderCrossingUseCase;
import it.gabriele.truckflow.application.port.out.BorderCrossingRepository;
import it.gabriele.truckflow.domain.customs.BorderCrossing;
import java.util.Objects;

/** Caso d'uso: registrare attraversamento confine. */
public final class DefaultRecordBorderCrossingUseCase implements RecordBorderCrossingUseCase {

  private final BorderCrossingRepository crossingRepository;

  public DefaultRecordBorderCrossingUseCase(BorderCrossingRepository crossingRepository) {
    this.crossingRepository =
        Objects.requireNonNull(crossingRepository, "Il repository transiti è obbligatorio.");
  }

  @Override
  public BorderCrossing handle(Command command) {
    Objects.requireNonNull(command, "Il comando transito doganale è obbligatorio.");
    BorderCrossing crossing =
        Objects.requireNonNull(command.crossing(), "L'attraversamento confine è obbligatorio.");
    crossingRepository.save(crossing);
    return crossing;
  }
}
