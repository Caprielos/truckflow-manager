package it.gabriele.truckflow.domain.driver;

/**
 * Rappresenta qualifiche professionali legate all'attività di trasporto.
 */
public enum DriverProfessionalQualification {

    CQC_GOODS("95");

    private final String harmonizedCode;

    DriverProfessionalQualification(String harmonizedCode) {
        this.harmonizedCode = harmonizedCode;
    }

    public String getHarmonizedCode() {
        return harmonizedCode;
    }
}
