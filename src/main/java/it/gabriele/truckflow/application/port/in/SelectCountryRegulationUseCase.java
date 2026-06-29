package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.regulation.EuropeanCountry;
import it.gabriele.truckflow.domain.regulation.TransportRegulatorySelection;

public interface SelectCountryRegulationUseCase {
  TransportRegulatorySelection handle(Command command);

  record Command(String tenantCode, EuropeanCountry country) {}
}
