package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.shared.Money;

public interface CalculateMissionRealMarginUseCase {
  Result handle(Command command);

  record Command(String missionCode, Money revenue) {}

  record Result(Money revenue, Money approvedCosts, Money margin) {}
}
