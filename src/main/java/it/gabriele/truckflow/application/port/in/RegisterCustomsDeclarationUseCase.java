package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.customs.CustomsDeclaration;

public interface RegisterCustomsDeclarationUseCase {

  CustomsDeclaration handle(Command command);

  record Command(CustomsDeclaration declaration) {}
}
