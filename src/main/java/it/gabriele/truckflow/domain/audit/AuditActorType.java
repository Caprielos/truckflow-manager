package it.gabriele.truckflow.domain.audit;

/**
 * Tipo di soggetto che ha generato un evento audit.
 */
public enum AuditActorType {

    USER(true),
    SYSTEM(false),
    INTEGRATION(false);

    private final boolean humanActor;

    AuditActorType(boolean humanActor) {
        this.humanActor = humanActor;
    }

    public boolean isHumanActor() {
        return humanActor;
    }
}
