package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterCustomerContractUseCase;
import it.gabriele.truckflow.application.port.out.CustomerContractRepository;
import it.gabriele.truckflow.domain.contract.CustomerContract;

import java.util.Objects;

/**
 * Caso d'uso: registrare un contratto/listino cliente.
 */
public final class DefaultRegisterCustomerContractUseCase implements RegisterCustomerContractUseCase {

    private final CustomerContractRepository repository;

    public DefaultRegisterCustomerContractUseCase(CustomerContractRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Il repository contratti cliente è obbligatorio.");
    }

    @Override
    public CustomerContract handle(Command command) {
        Objects.requireNonNull(command, "Il comando contratto cliente è obbligatorio.");
        repository.save(command.contract());
        return command.contract();
    }
}
