package it.gabriele.truckflow.domain.tire;

public final class TireRules {

    public static final double LEGAL_MINIMUM_TREAD_DEPTH_MM = 1.6;
    public static final double DEFAULT_COMPANY_WARNING_TREAD_DEPTH_MM = 4.0;

    private TireRules() {
    }

    public static boolean isLegallyUsable(Tire tire) {
        validate(tire);
        return !tire.isBelowLegalMinimum() && tire.getStatus() != TireStatus.DISPOSED;
    }

    public static boolean shouldScheduleReplacement(Tire tire, double companyWarningDepthMillimeters) {
        validate(tire);
        if (companyWarningDepthMillimeters < LEGAL_MINIMUM_TREAD_DEPTH_MM) {
            throw new IllegalArgumentException("La soglia aziendale non può essere sotto il minimo legale.");
        }
        return tire.getTreadDepthMillimeters() <= companyWarningDepthMillimeters;
    }

    public static boolean shouldScheduleReplacement(Tire tire) {
        return shouldScheduleReplacement(tire, DEFAULT_COMPANY_WARNING_TREAD_DEPTH_MM);
    }

    private static void validate(Tire tire) {
        if (tire == null) {
            throw new IllegalArgumentException("La gomma è obbligatoria.");
        }
    }
}
