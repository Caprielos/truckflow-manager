package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.GenerateShipmentDocumentBundleUseCase;
import it.gabriele.truckflow.application.port.out.ShipmentDocumentBundleRepository;
import it.gabriele.truckflow.application.port.out.ShipmentRepository;
import it.gabriele.truckflow.domain.document.ShipmentDocumentBundle;

import java.util.Objects;

/**
 * Caso d'uso: generare il fascicolo documentale richiesto per una spedizione.
 */
public final class DefaultGenerateShipmentDocumentBundleUseCase implements GenerateShipmentDocumentBundleUseCase {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentDocumentBundleRepository bundleRepository;

    public DefaultGenerateShipmentDocumentBundleUseCase(
            ShipmentRepository shipmentRepository,
            ShipmentDocumentBundleRepository bundleRepository
    ) {
        this.shipmentRepository = Objects.requireNonNull(shipmentRepository, "Il repository spedizioni è obbligatorio.");
        this.bundleRepository = Objects.requireNonNull(bundleRepository, "Il repository fascicoli documentali è obbligatorio.");
    }

    @Override
    public ShipmentDocumentBundle handle(Command command) {
        Objects.requireNonNull(command, "Il comando fascicolo documentale è obbligatorio.");
        shipmentRepository.getRequired(command.shipmentNumber(), "Spedizione");
        ShipmentDocumentBundle bundle = ShipmentDocumentBundle.of(
                command.bundleCode(),
                command.shipmentNumber(),
                command.requiredTypes(),
                command.documents(),
                command.notes()
        );
        bundleRepository.save(bundle);
        return bundle;
    }
}
