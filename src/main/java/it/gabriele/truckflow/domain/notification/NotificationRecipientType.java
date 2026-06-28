package it.gabriele.truckflow.domain.notification;

/**
 * Tipo di destinatario della notifica.
 */
public enum NotificationRecipientType {

    CUSTOMER_CONTACT(true),
    DRIVER(true),
    DISPATCHER(true),
    ADMIN(true),
    INTEGRATION(false),
    SYSTEM(false);

    private final boolean humanRecipient;

    NotificationRecipientType(boolean humanRecipient) {
        this.humanRecipient = humanRecipient;
    }

    public boolean isHumanRecipient() {
        return humanRecipient;
    }
}
