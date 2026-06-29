package it.gabriele.truckflow.application.usecase.roadinspection;

import it.gabriele.truckflow.application.port.in.roadinspection.RegisterRoadInspectionUseCase;
import it.gabriele.truckflow.application.port.out.roadinspection.RoadInspectionRepository;
import it.gabriele.truckflow.domain.roadinspection.RoadInspection;
import java.util.Objects;

/** Implementazione default di RegisterRoadInspectionUseCase. */
public final class DefaultRegisterRoadInspectionUseCase implements RegisterRoadInspectionUseCase {

  private final RoadInspectionRepository repository;

  public DefaultRegisterRoadInspectionUseCase(RoadInspectionRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public RoadInspection handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    RoadInspection aggregate =
        Objects.requireNonNull(command.inspection(), "Il controllo su strada è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
