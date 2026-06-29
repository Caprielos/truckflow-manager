package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.CustomsDeclarationRepository;
import it.gabriele.truckflow.domain.customs.CustomsDeclaration;

/** Repository in memoria per dichiarazioni doganali. */
public final class InMemoryCustomsDeclarationRepository
    extends InMemoryRepository<CustomsDeclaration> implements CustomsDeclarationRepository {

  public InMemoryCustomsDeclarationRepository() {
    super(CustomsDeclaration::getDeclarationCode);
  }
}
