package it.gabriele.truckflow.domain.kpi;

/** KPI enterprise misurabile sulla piattaforma TruckFlow. */
public enum KpiMetric {
  ON_TIME_DELIVERY_RATE(KpiCategory.OPERATIONAL),
  AVERAGE_DELAY_MINUTES(KpiCategory.OPERATIONAL),
  VEHICLE_SATURATION_RATE(KpiCategory.FLEET),
  DRIVER_SATURATION_RATE(KpiCategory.DRIVER),
  MARGIN_PER_MISSION(KpiCategory.FINANCIAL),
  MARGIN_PER_CUSTOMER(KpiCategory.FINANCIAL),
  COST_PER_KILOMETER(KpiCategory.FLEET),
  FUEL_CONSUMPTION_LITERS_PER_100KM(KpiCategory.FLEET),
  CLAIM_RATE(KpiCategory.QUALITY),
  POD_ON_TIME_RATE(KpiCategory.QUALITY),
  DOCUMENT_COMPLETENESS_RATE(KpiCategory.COMPLIANCE),
  VEHICLE_DOWNTIME_HOURS(KpiCategory.FLEET);

  private final KpiCategory category;

  KpiMetric(KpiCategory category) {
    this.category = category;
  }

  public KpiCategory getCategory() {
    return category;
  }
}
