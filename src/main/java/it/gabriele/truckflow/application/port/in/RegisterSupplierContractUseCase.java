package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.suppliercontract.SupplierContract;

public interface RegisterSupplierContractUseCase {
  SupplierContract handle(Command command);

  record Command(SupplierContract contract) {}
}
