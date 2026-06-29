package it.gabriele.truckflow.infrastructure.memory.financeops;

import it.gabriele.truckflow.application.port.out.financeops.BankTransactionRepository;
import it.gabriele.truckflow.domain.financeops.BankTransaction;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per BankTransaction. */
public final class InMemoryBankTransactionRepository extends InMemoryRepository<BankTransaction>
    implements BankTransactionRepository {

  public InMemoryBankTransactionRepository() {
    super(transaction -> transaction.transactionCode());
  }
}
