package it.gabriele.truckflow.domain.customer;

/** Rappresenta il tipo di cliente. */
public enum CustomerType {
  INDIVIDUAL(false),
  COMPANY(true),
  PUBLIC_AUTHORITY(true),
  INTERNAL(true);

  private final boolean businessCustomer;

  CustomerType(boolean businessCustomer) {
    this.businessCustomer = businessCustomer;
  }

  public boolean isBusinessCustomer() {
    return businessCustomer;
  }
}
