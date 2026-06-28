package it.gabriele.truckflow.domain.driver;

/**
 * Rappresenta le categorie di patente usate nel dominio.
 * In questo progetto usiamo solo B, C ed E.
 */
public enum DriverLicenseCategory {

    B(false, false),
    C(true, false),
    E(false, true);

    private final boolean heavyGoodsLicense;
    private final boolean trailerExtension;

    DriverLicenseCategory(boolean heavyGoodsLicense, boolean trailerExtension) {
        this.heavyGoodsLicense = heavyGoodsLicense;
        this.trailerExtension = trailerExtension;
    }

    public boolean isHeavyGoodsLicense() {
        return heavyGoodsLicense;
    }

    public boolean isTrailerExtension() {
        return trailerExtension;
    }
}
