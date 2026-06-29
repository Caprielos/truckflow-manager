package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.carrierliability.CarrierLiabilityCase;

public interface OpenCarrierLiabilityCaseUseCase {
  CarrierLiabilityCase handle(Command command);

  record Command(CarrierLiabilityCase liabilityCase) {}
}
