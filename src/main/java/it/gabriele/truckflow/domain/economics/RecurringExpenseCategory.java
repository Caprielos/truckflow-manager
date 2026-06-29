package it.gabriele.truckflow.domain.economics;

/**
 * Costi ricorrenti o generali che in un'azienda di trasporto hanno un prezzo anche se non stanno in
 * una singola missione.
 */
public enum RecurringExpenseCategory {
  INSURANCE,
  ROAD_TAX,
  VEHICLE_FINANCING_INSTALLMENT,
  TRAILER_FINANCING_INSTALLMENT,
  LEASING_RENTAL,
  VEHICLE_RENTAL,
  DRIVER_SALARY,
  SOCIAL_CONTRIBUTIONS,
  ACCOUNTANT,
  OFFICE_RENT,
  WAREHOUSE_RENT,
  UTILITIES,
  SOFTWARE_SUBSCRIPTION,
  TELEMATICS_SUBSCRIPTION,
  MOBILE_PHONE,
  INTERNET,
  BANK_FEES,
  LICENSE_OR_PERMIT,
  MEMBERSHIP,
  TRAINING,
  SECURITY,
  CLEANING,
  ADMINISTRATIVE,
  OTHER;

  public boolean isVehicleRelated() {
    return switch (this) {
      case INSURANCE,
          ROAD_TAX,
          VEHICLE_FINANCING_INSTALLMENT,
          TRAILER_FINANCING_INSTALLMENT,
          LEASING_RENTAL,
          VEHICLE_RENTAL,
          TELEMATICS_SUBSCRIPTION ->
          true;
      default -> false;
    };
  }
}
