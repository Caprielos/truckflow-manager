package it.gabriele.truckflow.domain.financeops;

import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.shared.Money;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class FinanceOperationsEnterpriseTest {

  @Test
  void shouldHandleElectronicInvoiceBankReconciliationAndCostAllocation() {
    ElectronicInvoiceEnvelope invoice =
        new ElectronicInvoiceEnvelope(
            "inv-001",
            "cust-001",
            FinancialDocumentType.ELECTRONIC_INVOICE,
            ElectronicInvoiceStatus.READY_TO_SEND,
            Money.of("1200.00", "EUR"),
            "",
            LocalDate.of(2026, 6, 29),
            LocalDate.of(2026, 7, 29));
    BankTransaction transaction =
        new BankTransaction(
            "bank-001",
            BankTransactionType.UNKNOWN,
            LocalDate.of(2026, 6, 29),
            Money.of("1200.00", "EUR"),
            "cust-001",
            "bonifico cliente",
            false);
    CostAllocation allocation =
        new CostAllocation(
            "cost-001",
            CostAllocationCategory.TOLL,
            Money.of("89.50", "EUR"),
            "mission-001",
            "truck-001",
            "cust-001",
            "toll-001",
            true);

    assertTrue(FinanceOperationRules.canSendElectronicInvoice(invoice));
    assertTrue(FinanceOperationRules.requiresAccountingAlert(transaction));
    assertTrue(FinanceOperationRules.canUseForProfitability(allocation));
  }
}
