package it.gabriele.truckflow.domain.suppliercontract;

import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.shared.Money;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SupplierContractEnterpriseTest {

  @Test
  void shouldValidateSubcontractorContractForMissionAssignment() {
    SupplierRateRule rule =
        new SupplierRateRule(
            "rate-001", SupplierRateType.PER_KILOMETER, Money.of("1.45", "EUR"), "IT-NORTH", false);
    SupplierContract contract =
        new SupplierContract(
            "supcon-001",
            "sub-001",
            SupplierContractStatus.ACTIVE,
            LocalDate.of(2026, 1, 1),
            LocalDate.of(2026, 12, 31),
            Set.of(SubcontractorServiceType.ADR_TRANSPORT),
            Set.of(rule),
            true,
            true,
            true);

    assertTrue(
        SupplierContractRules.canAssignMission(
            contract, SubcontractorServiceType.ADR_TRANSPORT, LocalDate.of(2026, 6, 29)));
  }
}
