package it.gabriele.truckflow.domain.financeops;

/** Regole amministrative: riconciliazione, fattura elettronica e allocazione costi reali. */
public final class FinanceOperationRules {

  private FinanceOperationRules() {}

  public static boolean canSendElectronicInvoice(ElectronicInvoiceEnvelope invoice) {
    if (invoice == null) {
      throw new IllegalArgumentException("La fattura è obbligatoria.");
    }
    return invoice.status() == ElectronicInvoiceStatus.READY_TO_SEND;
  }

  public static boolean requiresAccountingAlert(BankTransaction transaction) {
    if (transaction == null) {
      throw new IllegalArgumentException("Il movimento bancario è obbligatorio.");
    }
    return transaction.requiresManualReconciliation();
  }

  public static boolean canUseForProfitability(CostAllocation allocation) {
    if (allocation == null) {
      throw new IllegalArgumentException("L'allocazione costo è obbligatoria.");
    }
    return allocation.approved() && allocation.hasOperationalTarget();
  }
}
