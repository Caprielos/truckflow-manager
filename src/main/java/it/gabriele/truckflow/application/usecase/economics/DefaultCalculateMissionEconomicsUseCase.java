package it.gabriele.truckflow.application.usecase.economics;

import it.gabriele.truckflow.application.port.in.economics.CalculateMissionEconomicsUseCase;
import it.gabriele.truckflow.application.port.out.economics.MissionEconomicsRepository;
import it.gabriele.truckflow.application.port.out.operation.TransportMissionRepository;
import it.gabriele.truckflow.application.port.out.shipment.ShipmentRepository;
import it.gabriele.truckflow.domain.economics.MissionEconomics;
import java.util.Objects;

/** Caso d'uso: calcolare e salvare il conto economico di una missione. */
public final class DefaultCalculateMissionEconomicsUseCase
    implements CalculateMissionEconomicsUseCase {

  private final TransportMissionRepository missionRepository;
  private final ShipmentRepository shipmentRepository;
  private final MissionEconomicsRepository economicsRepository;

  public DefaultCalculateMissionEconomicsUseCase(
      TransportMissionRepository missionRepository,
      ShipmentRepository shipmentRepository,
      MissionEconomicsRepository economicsRepository) {
    this.missionRepository =
        Objects.requireNonNull(missionRepository, "Il repository missioni è obbligatorio.");
    this.shipmentRepository =
        Objects.requireNonNull(shipmentRepository, "Il repository spedizioni è obbligatorio.");
    this.economicsRepository =
        Objects.requireNonNull(
            economicsRepository, "Il repository economics missione è obbligatorio.");
  }

  @Override
  public MissionEconomics handle(Command command) {
    Objects.requireNonNull(command, "Il comando economics missione è obbligatorio.");
    missionRepository.getRequired(command.missionNumber(), "Missione");
    shipmentRepository.getRequired(command.shipmentNumber(), "Spedizione");
    MissionEconomics economics =
        MissionEconomics.of(
            command.missionNumber(),
            command.shipmentNumber(),
            command.revenueLines(),
            command.costLines(),
            command.notes());
    economicsRepository.save(economics);
    return economics;
  }
}
