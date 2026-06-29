package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.sla.ServiceLevelAgreement;

public interface RegisterServiceLevelAgreementUseCase {

  ServiceLevelAgreement handle(Command command);

  record Command(ServiceLevelAgreement agreement) {}
}
