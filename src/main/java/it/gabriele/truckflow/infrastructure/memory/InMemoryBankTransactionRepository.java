package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.BankTransactionRepository;
import it.gabriele.truckflow.domain.financeops.BankTransaction;

/** Repository in memoria per BankTransaction. */
public final class InMemoryBankTransactionRepository extends InMemoryRepository<BankTransaction>
    implements BankTransactionRepository {

  public InMemoryBankTransactionRepository() {
    super(transaction -> transaction.transactionCode());
  }
}
