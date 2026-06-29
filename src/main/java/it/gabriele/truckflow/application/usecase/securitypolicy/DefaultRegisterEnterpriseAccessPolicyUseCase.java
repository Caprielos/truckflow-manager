package it.gabriele.truckflow.application.usecase.securitypolicy;

import it.gabriele.truckflow.application.port.in.securitypolicy.RegisterEnterpriseAccessPolicyUseCase;
import it.gabriele.truckflow.application.port.out.securitypolicy.EnterpriseAccessPolicyRepository;
import it.gabriele.truckflow.domain.securitypolicy.EnterpriseAccessPolicy;
import java.util.Objects;

/** Implementazione default di RegisterEnterpriseAccessPolicyUseCase. */
public final class DefaultRegisterEnterpriseAccessPolicyUseCase
    implements RegisterEnterpriseAccessPolicyUseCase {

  private final EnterpriseAccessPolicyRepository repository;

  public DefaultRegisterEnterpriseAccessPolicyUseCase(EnterpriseAccessPolicyRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public EnterpriseAccessPolicy handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    EnterpriseAccessPolicy aggregate =
        Objects.requireNonNull(command.policy(), "La policy sicurezza è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
