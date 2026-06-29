package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.document.TransportDocumentType;
import it.gabriele.truckflow.domain.fleet.VehicleCertificateType;
import java.util.Set;

/** Regole operative derivate dalla categoria merce. */
public final class CargoOperationalRules {

  private CargoOperationalRules() {}

  public static Set<TransportDocumentType> requiredDocumentsFor(CargoCategory category) {
    validateCategory(category);

    if (category.requiresFirDocument()) {
      return Set.of(TransportDocumentType.WASTE_IDENTIFICATION_FORM);
    }

    if (category.requiresAdrData()) {
      return Set.of(
          TransportDocumentType.ADR_TRANSPORT_DOCUMENT,
          TransportDocumentType.SAFETY_DATA_SHEET,
          TransportDocumentType.ADR_WRITTEN_INSTRUCTIONS);
    }

    if (category == CargoCategory.LIVESTOCK) {
      return Set.of(TransportDocumentType.VETERINARY_DOCUMENT);
    }

    if (category.requiresSanitaryOrVeterinaryDocuments()) {
      return Set.of(TransportDocumentType.HACCP_SANITATION_DOCUMENT);
    }

    return Set.of(TransportDocumentType.DELIVERY_NOTE);
  }

  public static Set<VehicleCertificateType> requiredVehicleCertificatesFor(CargoCategory category) {
    validateCategory(category);

    if (category.requiresTemperatureControl()) {
      return Set.of(VehicleCertificateType.ATP);
    }

    if (category.requiresAdrData()) {
      return Set.of(VehicleCertificateType.ADR_VEHICLE_APPROVAL);
    }

    return Set.of(VehicleCertificateType.ROADWORTHINESS_INSPECTION);
  }

  public static boolean requiresEerCode(CargoCategory category) {
    validateCategory(category);
    return category.requiresEerCode();
  }

  public static boolean requiresUnNumber(CargoCategory category) {
    validateCategory(category);
    return category.requiresAdrData();
  }

  private static void validateCategory(CargoCategory category) {
    if (category == null) {
      throw new IllegalArgumentException("La categoria merce è obbligatoria.");
    }
  }
}
