package it.gabriele.truckflow.infrastructure.memory.contract;

import it.gabriele.truckflow.application.port.out.contract.ContractRateCardRepository;
import it.gabriele.truckflow.domain.contract.ContractRateCard;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per ContractRateCard. */
public final class InMemoryContractRateCardRepository extends InMemoryRepository<ContractRateCard>
    implements ContractRateCardRepository {

  public InMemoryContractRateCardRepository() {
    super(item -> item.getRateCardCode());
  }
}
