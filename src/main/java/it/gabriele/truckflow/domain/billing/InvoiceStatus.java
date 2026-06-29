package it.gabriele.truckflow.domain.billing;

/** Stato di una fattura. */
public enum InvoiceStatus {
  DRAFT(false, false),
  ISSUED(false, true),
  PAID(true, false),
  CANCELLED(true, false);

  private final boolean terminal;
  private final boolean canReceivePayment;

  InvoiceStatus(boolean terminal, boolean canReceivePayment) {
    this.terminal = terminal;
    this.canReceivePayment = canReceivePayment;
  }

  public boolean isTerminal() {
    return terminal;
  }

  public boolean canReceivePayment() {
    return canReceivePayment;
  }
}
