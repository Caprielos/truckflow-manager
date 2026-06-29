package it.gabriele.truckflow.domain.financeops;

/** Tipo movimento bancario. */
public enum BankTransactionType {
  CUSTOMER_PAYMENT,
  SUPPLIER_PAYMENT,
  FUEL_CARD_DEBIT,
  TOLL_DEBIT,
  PAYROLL_PAYMENT,
  INSURANCE_PAYMENT,
  TAX_PAYMENT,
  SEPA_DIRECT_DEBIT,
  BANK_FEE,
  UNKNOWN
}
