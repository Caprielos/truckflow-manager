package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.ContractRateCardRepository;
import it.gabriele.truckflow.domain.contract.ContractRateCard;

/** Repository in memoria per ContractRateCard. */
public final class InMemoryContractRateCardRepository extends InMemoryRepository<ContractRateCard> implements ContractRateCardRepository {

    public InMemoryContractRateCardRepository() {
        super(item -> item.getRateCardCode());
    }
}
