package it.gabriele.truckflow.domain.suppliercontract;

import java.time.LocalDate;
import java.util.Set;

/** Contratto fornitore/sub-vettore con servizi, validità, SLA e assicurazione. */
public record SupplierContract(
    String contractCode,
    String supplierCode,
    SupplierContractStatus status,
    LocalDate validFrom,
    LocalDate validTo,
    Set<SubcontractorServiceType> serviceTypes,
    Set<SupplierRateRule> rateRules,
    boolean insuranceVerified,
    boolean complianceDocumentsVerified,
    boolean slaAccepted) {

  public SupplierContract {
    contractCode = normalize(contractCode, "Il codice contratto fornitore è obbligatorio.");
    supplierCode = normalize(supplierCode, "Il codice fornitore è obbligatorio.");
    if (status == null) {
      throw new IllegalArgumentException("Lo stato contratto è obbligatorio.");
    }
    if (validFrom == null || validTo == null || validTo.isBefore(validFrom)) {
      throw new IllegalArgumentException("La validità contratto non è corretta.");
    }
    serviceTypes = serviceTypes == null ? Set.of() : Set.copyOf(serviceTypes);
    rateRules = rateRules == null ? Set.of() : Set.copyOf(rateRules);
  }

  public boolean provides(SubcontractorServiceType serviceType) {
    if (serviceType == null) {
      throw new IllegalArgumentException("Il servizio da verificare è obbligatorio.");
    }
    return serviceTypes.contains(serviceType);
  }

  public boolean isValidOn(LocalDate date) {
    return date != null && !date.isBefore(validFrom) && !date.isAfter(validTo);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
