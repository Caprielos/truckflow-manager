package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.ReconcileBankTransactionUseCase;
import it.gabriele.truckflow.application.port.out.BankTransactionRepository;
import it.gabriele.truckflow.domain.financeops.BankTransaction;

/** Implementazione default di ReconcileBankTransactionUseCase. */
public final class DefaultReconcileBankTransactionUseCase
    implements ReconcileBankTransactionUseCase {

  private final BankTransactionRepository transactionRepository;

  public DefaultReconcileBankTransactionUseCase(BankTransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public BankTransaction handle(Command command) {
    BankTransaction current =
        transactionRepository.getRequired(command.transactionCode(), "Movimento bancario");
    BankTransaction reconciled =
        new BankTransaction(
            current.transactionCode(),
            current.transactionType(),
            current.bookingDate(),
            current.amount(),
            current.counterpartyCode(),
            current.referenceText(),
            true);
    transactionRepository.save(reconciled);
    return reconciled;
  }
}
