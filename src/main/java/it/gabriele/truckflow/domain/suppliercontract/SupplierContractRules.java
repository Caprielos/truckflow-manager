package it.gabriele.truckflow.domain.suppliercontract;

import java.time.LocalDate;

/** Regole per contratti fornitori e sub-vettori. */
public final class SupplierContractRules {

  private SupplierContractRules() {}

  public static boolean canAssignMission(
      SupplierContract contract, SubcontractorServiceType requiredService, LocalDate missionDate) {
    if (contract == null || requiredService == null || missionDate == null) {
      throw new IllegalArgumentException("Contratto, servizio e data missione sono obbligatori.");
    }
    return contract.status() == SupplierContractStatus.ACTIVE
        && contract.isValidOn(missionDate)
        && contract.provides(requiredService)
        && contract.insuranceVerified()
        && contract.complianceDocumentsVerified()
        && contract.slaAccepted();
  }

  public static boolean requiresCommercialApproval(SupplierRateRule rule) {
    if (rule == null) {
      throw new IllegalArgumentException("La regola tariffaria è obbligatoria.");
    }
    return rule.requiresPreApproval()
        || rule.rateType() == SupplierRateType.CUSTOMS_FEE
        || rule.rateType() == SupplierRateType.FIXED_MONTHLY_FEE;
  }
}
