package it.gabriele.truckflow.infrastructure.memory.suppliercontract;

import it.gabriele.truckflow.application.port.out.SupplierContractRepository;
import it.gabriele.truckflow.domain.suppliercontract.SupplierContract;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per SupplierContract. */
public final class InMemorySupplierContractRepository extends InMemoryRepository<SupplierContract>
    implements SupplierContractRepository {

  public InMemorySupplierContractRepository() {
    super(contract -> contract.contractCode());
  }
}
