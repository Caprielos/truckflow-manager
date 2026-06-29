package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.SelectCountryRegulationUseCase;
import it.gabriele.truckflow.application.port.out.TransportRegulatorySelectionRepository;
import it.gabriele.truckflow.domain.regulation.CountryRegulatoryProfile;
import it.gabriele.truckflow.domain.regulation.RoadTransportRegulationCatalog;
import it.gabriele.truckflow.domain.regulation.TransportRegulatorySelection;
import java.time.LocalDate;
import java.util.Objects;

/** Implementazione default di SelectCountryRegulationUseCase. */
public final class DefaultSelectCountryRegulationUseCase implements SelectCountryRegulationUseCase {

  private final TransportRegulatorySelectionRepository repository;

  public DefaultSelectCountryRegulationUseCase(TransportRegulatorySelectionRepository repository) {
    this.repository =
        Objects.requireNonNull(repository, "Il repository selezione normativa è obbligatorio.");
  }

  @Override
  public TransportRegulatorySelection handle(Command command) {
    Objects.requireNonNull(command, "Il comando selezione normativa è obbligatorio.");
    CountryRegulatoryProfile profile = RoadTransportRegulationCatalog.forCountry(command.country());
    TransportRegulatorySelection selection =
        new TransportRegulatorySelection(
            command.tenantCode(), command.country(), profile, LocalDate.now());
    repository.save(selection);
    return selection;
  }
}
