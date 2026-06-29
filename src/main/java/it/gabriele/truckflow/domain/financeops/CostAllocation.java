package it.gabriele.truckflow.domain.financeops;

import it.gabriele.truckflow.domain.shared.Money;

/** Allocazione di costo reale su missione, veicolo, cliente o centro di costo. */
public record CostAllocation(
    String allocationCode,
    CostAllocationCategory category,
    Money amount,
    String missionCode,
    String vehicleCode,
    String customerCode,
    String sourceDocumentCode,
    boolean approved) {

  public CostAllocation {
    allocationCode = normalize(allocationCode, "Il codice allocazione è obbligatorio.");
    if (category == null) {
      throw new IllegalArgumentException("La categoria costo è obbligatoria.");
    }
    if (amount == null) {
      throw new IllegalArgumentException("L'importo costo è obbligatorio.");
    }
    missionCode = missionCode == null ? "" : missionCode.trim().toUpperCase();
    vehicleCode = vehicleCode == null ? "" : vehicleCode.trim().toUpperCase();
    customerCode = customerCode == null ? "" : customerCode.trim().toUpperCase();
    sourceDocumentCode = sourceDocumentCode == null ? "" : sourceDocumentCode.trim().toUpperCase();
  }

  public boolean hasOperationalTarget() {
    return !missionCode.isEmpty() || !vehicleCode.isEmpty() || !customerCode.isEmpty();
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
