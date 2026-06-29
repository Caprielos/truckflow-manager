package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.financeops.BankTransaction;

public interface ReconcileBankTransactionUseCase {
  BankTransaction handle(Command command);

  record Command(String transactionCode) {}
}
