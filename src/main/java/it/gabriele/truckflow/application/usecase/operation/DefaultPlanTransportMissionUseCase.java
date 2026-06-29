package it.gabriele.truckflow.application.usecase.operation;

import it.gabriele.truckflow.application.port.in.operation.PlanTransportMissionUseCase;
import it.gabriele.truckflow.application.port.out.DriverRepository;
import it.gabriele.truckflow.application.port.out.RoutePlanRepository;
import it.gabriele.truckflow.application.port.out.ShipmentRepository;
import it.gabriele.truckflow.application.port.out.TransportMissionRepository;
import it.gabriele.truckflow.application.port.out.VehicleCombinationRepository;
import it.gabriele.truckflow.domain.driver.Driver;
import it.gabriele.truckflow.domain.fleet.VehicleCombination;
import it.gabriele.truckflow.domain.operation.TransportMission;
import it.gabriele.truckflow.domain.route.RoutePlan;
import it.gabriele.truckflow.domain.shipment.Shipment;
import java.util.Objects;

/** Caso d'uso: pianificare una missione reale con spedizione, autista, convoglio e route plan. */
public final class DefaultPlanTransportMissionUseCase implements PlanTransportMissionUseCase {

  private final ShipmentRepository shipmentRepository;
  private final DriverRepository driverRepository;
  private final VehicleCombinationRepository vehicleCombinationRepository;
  private final RoutePlanRepository routePlanRepository;
  private final TransportMissionRepository missionRepository;

  public DefaultPlanTransportMissionUseCase(
      ShipmentRepository shipmentRepository,
      DriverRepository driverRepository,
      VehicleCombinationRepository vehicleCombinationRepository,
      RoutePlanRepository routePlanRepository,
      TransportMissionRepository missionRepository) {
    this.shipmentRepository =
        Objects.requireNonNull(shipmentRepository, "Il repository spedizioni è obbligatorio.");
    this.driverRepository =
        Objects.requireNonNull(driverRepository, "Il repository autisti è obbligatorio.");
    this.vehicleCombinationRepository =
        Objects.requireNonNull(
            vehicleCombinationRepository, "Il repository convogli è obbligatorio.");
    this.routePlanRepository =
        Objects.requireNonNull(routePlanRepository, "Il repository route plan è obbligatorio.");
    this.missionRepository =
        Objects.requireNonNull(missionRepository, "Il repository missioni è obbligatorio.");
  }

  @Override
  public TransportMission handle(Command command) {
    Objects.requireNonNull(command, "Il comando pianificazione missione è obbligatorio.");
    Shipment shipment = shipmentRepository.getRequired(command.shipmentNumber(), "Spedizione");
    Driver driver = driverRepository.getRequired(command.driverId(), "Autista");
    VehicleCombination combination =
        vehicleCombinationRepository.getRequired(command.vehicleCombinationId(), "Convoglio");
    RoutePlan routePlan = routePlanRepository.getRequired(command.routePlanId(), "Route plan");

    TransportMission mission =
        TransportMission.planned(
            command.missionNumber(), shipment, driver, combination, routePlan, command.notes());
    missionRepository.save(mission);
    return mission;
  }
}
