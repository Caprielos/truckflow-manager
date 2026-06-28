package it.gabriele.truckflow.domain.driver;

/**
 * Categorie patente per autotrasporto.
 */
public enum DriverLicenseCategory {

    B(false, false),
    C1(true, false),
    C(true, false),
    BE(false, true),
    C1E(true, true),
    CE(true, true),

    /** @deprecated usare BE, C1E o CE in base alla categoria principale. */
    @Deprecated
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
