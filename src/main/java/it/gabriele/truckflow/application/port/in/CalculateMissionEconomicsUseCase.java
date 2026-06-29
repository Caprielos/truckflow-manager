package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.economics.MissionCostLine;
import it.gabriele.truckflow.domain.economics.MissionEconomics;
import it.gabriele.truckflow.domain.economics.MissionRevenueLine;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.List;

public interface CalculateMissionEconomicsUseCase {

  MissionEconomics handle(Command command);

  record Command(
      String missionNumber,
      String shipmentNumber,
      List<MissionRevenueLine> revenueLines,
      List<MissionCostLine> costLines,
      Notes notes) {}
}
