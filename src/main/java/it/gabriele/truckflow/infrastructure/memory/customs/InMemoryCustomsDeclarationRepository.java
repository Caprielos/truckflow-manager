package it.gabriele.truckflow.infrastructure.memory.customs;

import it.gabriele.truckflow.application.port.out.CustomsDeclarationRepository;
import it.gabriele.truckflow.domain.customs.CustomsDeclaration;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per dichiarazioni doganali. */
public final class InMemoryCustomsDeclarationRepository
    extends InMemoryRepository<CustomsDeclaration> implements CustomsDeclarationRepository {

  public InMemoryCustomsDeclarationRepository() {
    super(CustomsDeclaration::getDeclarationCode);
  }
}
