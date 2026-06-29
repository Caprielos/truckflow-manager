package it.gabriele.truckflow.application.port.in.contract;

import it.gabriele.truckflow.domain.contract.CustomerContract;

public interface RegisterCustomerContractUseCase {

  CustomerContract handle(Command command);

  record Command(CustomerContract contract) {}
}
