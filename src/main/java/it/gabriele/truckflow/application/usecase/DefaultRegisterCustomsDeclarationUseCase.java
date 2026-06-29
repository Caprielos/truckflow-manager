package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterCustomsDeclarationUseCase;
import it.gabriele.truckflow.application.port.out.CustomsDeclarationRepository;
import it.gabriele.truckflow.domain.customs.CustomsDeclaration;
import java.util.Objects;

/** Caso d'uso: registrare dichiarazione doganale. */
public final class DefaultRegisterCustomsDeclarationUseCase
    implements RegisterCustomsDeclarationUseCase {

  private final CustomsDeclarationRepository customsRepository;

  public DefaultRegisterCustomsDeclarationUseCase(CustomsDeclarationRepository customsRepository) {
    this.customsRepository =
        Objects.requireNonNull(customsRepository, "Il repository dogana è obbligatorio.");
  }

  @Override
  public CustomsDeclaration handle(Command command) {
    Objects.requireNonNull(command, "Il comando dichiarazione doganale è obbligatorio.");
    CustomsDeclaration declaration =
        Objects.requireNonNull(command.declaration(), "La dichiarazione doganale è obbligatoria.");
    customsRepository.save(declaration);
    return declaration;
  }
}
