package it.gabriele.truckflow.application.usecase.suppliercontract;

import it.gabriele.truckflow.application.port.in.RegisterSupplierContractUseCase;
import it.gabriele.truckflow.application.port.out.SupplierContractRepository;
import it.gabriele.truckflow.domain.suppliercontract.SupplierContract;
import java.util.Objects;

/** Implementazione default di RegisterSupplierContractUseCase. */
public final class DefaultRegisterSupplierContractUseCase
    implements RegisterSupplierContractUseCase {

  private final SupplierContractRepository repository;

  public DefaultRegisterSupplierContractUseCase(SupplierContractRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public SupplierContract handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    SupplierContract aggregate =
        Objects.requireNonNull(command.contract(), "Il contratto fornitore è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
