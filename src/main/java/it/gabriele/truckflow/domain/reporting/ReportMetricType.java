package it.gabriele.truckflow.domain.reporting;

/**
 * Tipo di metrica presente in un report.
 */
public enum ReportMetricType {

    SHIPMENT_COUNT(false, false, "shipments"),
    COMPLETED_SHIPMENT_COUNT(false, false, "shipments"),
    DELAY_COUNT(false, false, "delays"),
    CLAIM_COUNT(false, false, "claims"),
    DOCUMENT_EXPIRATION_COUNT(false, false, "documents"),
    MAINTENANCE_COUNT(false, false, "work orders"),

    TOTAL_DISTANCE_KM(false, false, "km"),
    TOTAL_REVENUE(true, false, "EUR"),
    TOTAL_COST(true, false, "EUR"),
    TOTAL_CO2_KG(false, false, "kg"),
    VEHICLE_UTILIZATION_PERCENTAGE(false, true, "%"),
    ON_TIME_DELIVERY_PERCENTAGE(false, true, "%");

    private final boolean monetary;
    private final boolean percentage;
    private final String defaultUnit;

    ReportMetricType(
            boolean monetary,
            boolean percentage,
            String defaultUnit
    ) {
        this.monetary = monetary;
        this.percentage = percentage;
        this.defaultUnit = defaultUnit;
    }

    public boolean isMonetary() {
        return monetary;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public String getDefaultUnit() {
        return defaultUnit;
    }
}
