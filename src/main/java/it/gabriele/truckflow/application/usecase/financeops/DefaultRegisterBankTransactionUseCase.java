package it.gabriele.truckflow.application.usecase.financeops;

import it.gabriele.truckflow.application.port.in.RegisterBankTransactionUseCase;
import it.gabriele.truckflow.application.port.out.BankTransactionRepository;
import it.gabriele.truckflow.domain.financeops.BankTransaction;
import java.util.Objects;

/** Implementazione default di RegisterBankTransactionUseCase. */
public final class DefaultRegisterBankTransactionUseCase implements RegisterBankTransactionUseCase {

  private final BankTransactionRepository repository;

  public DefaultRegisterBankTransactionUseCase(BankTransactionRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public BankTransaction handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    BankTransaction aggregate =
        Objects.requireNonNull(command.transaction(), "Il movimento bancario è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
