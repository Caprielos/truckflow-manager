package it.gabriele.truckflow.domain.pricing;

/**
 * Fonte della stima costi percorso.
 * Nel domain non chiamiamo servizi esterni: salviamo solo da dove arriva la stima.
 */
public enum CostEstimationSource {

    MANUAL(false),
    INTERNAL_MODEL(false),
    VIAMICHELIN(true),
    HERE_MAPS(true),
    PTV(true),
    GOOGLE_MAPS(true),
    OTHER_EXTERNAL_PROVIDER(true);

    private final boolean externalProvider;

    CostEstimationSource(boolean externalProvider) {
        this.externalProvider = externalProvider;
    }

    public boolean isExternalProvider() {
        return externalProvider;
    }

    public boolean isManual() {
        return this == MANUAL;
    }
}
