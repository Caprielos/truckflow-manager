package it.gabriele.truckflow.application.port.in.financeops;

import it.gabriele.truckflow.domain.financeops.BankTransaction;

public interface RegisterBankTransactionUseCase {
  BankTransaction handle(Command command);

  record Command(BankTransaction transaction) {}
}
