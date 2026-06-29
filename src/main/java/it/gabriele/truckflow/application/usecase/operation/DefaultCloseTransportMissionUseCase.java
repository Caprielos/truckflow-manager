package it.gabriele.truckflow.application.usecase.operation;

import it.gabriele.truckflow.application.port.in.operation.CloseTransportMissionUseCase;
import it.gabriele.truckflow.application.port.out.TransportMissionRepository;
import it.gabriele.truckflow.domain.operation.TransportMission;
import java.util.Objects;

/** Caso d'uso: chiudere una missione operativa completata. */
public final class DefaultCloseTransportMissionUseCase implements CloseTransportMissionUseCase {

  private final TransportMissionRepository missionRepository;

  public DefaultCloseTransportMissionUseCase(TransportMissionRepository missionRepository) {
    this.missionRepository =
        Objects.requireNonNull(missionRepository, "Il repository missioni è obbligatorio.");
  }

  @Override
  public TransportMission handle(Command command) {
    Objects.requireNonNull(command, "Il comando chiusura missione è obbligatorio.");
    TransportMission mission = missionRepository.getRequired(command.missionNumber(), "Missione");
    TransportMission completed = mission.complete();
    missionRepository.save(completed);
    return completed;
  }
}
