package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.SupplierContractRepository;
import it.gabriele.truckflow.domain.suppliercontract.SupplierContract;

/** Repository in memoria per SupplierContract. */
public final class InMemorySupplierContractRepository extends InMemoryRepository<SupplierContract>
    implements SupplierContractRepository {

  public InMemorySupplierContractRepository() {
    super(contract -> contract.contractCode());
  }
}
