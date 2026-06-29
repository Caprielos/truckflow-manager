package it.gabriele.truckflow.domain.notification;

/** Tipo di notifica generata dal sistema. */
public enum NotificationType {
  SHIPMENT_PLANNED(true, true, false, false),
  SHIPMENT_DELAYED(true, true, false, false),
  PICKUP_COMPLETED(true, true, false, false),
  DELIVERY_COMPLETED(true, true, false, false),

  DOCUMENT_REQUESTED(true, true, false, false),
  DOCUMENT_VERIFIED(false, true, false, false),

  INVOICE_ISSUED(true, false, true, false),
  PAYMENT_RECEIVED(true, false, true, false),

  CLAIM_UPDATED(true, false, false, false),
  MAINTENANCE_ALERT(false, true, false, false),

  SECURITY_ALERT(false, false, false, true),
  SYSTEM_ALERT(false, false, false, true);

  private final boolean customerVisible;
  private final boolean operational;
  private final boolean financial;
  private final boolean security;

  NotificationType(
      boolean customerVisible, boolean operational, boolean financial, boolean security) {
    this.customerVisible = customerVisible;
    this.operational = operational;
    this.financial = financial;
    this.security = security;
  }

  public boolean isCustomerVisible() {
    return customerVisible;
  }

  public boolean isOperational() {
    return operational;
  }

  public boolean isFinancial() {
    return financial;
  }

  public boolean isSecurity() {
    return security;
  }
}
