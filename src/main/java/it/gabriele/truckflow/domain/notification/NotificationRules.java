package it.gabriele.truckflow.domain.notification;

/**
 * Regole di dominio per le notifiche.
 */
public final class NotificationRules {

    private NotificationRules() {
    }

    public static boolean canBeScheduled(NotificationMessage message) {
        validateMessage(message);

        return message.getStatus() == NotificationStatus.DRAFT;
    }

    public static boolean canBeSent(NotificationMessage message) {
        validateMessage(message);

        return message.getStatus() == NotificationStatus.DRAFT
                || message.getStatus() == NotificationStatus.SCHEDULED;
    }

    public static boolean canBeFailed(NotificationMessage message) {
        validateMessage(message);

        return message.getStatus() == NotificationStatus.DRAFT
                || message.getStatus() == NotificationStatus.SCHEDULED;
    }

    public static boolean canBeCancelled(NotificationMessage message) {
        validateMessage(message);

        return message.getStatus() == NotificationStatus.DRAFT
                || message.getStatus() == NotificationStatus.SCHEDULED;
    }

    public static boolean isTerminal(NotificationMessage message) {
        validateMessage(message);

        return message.isTerminal();
    }

    public static boolean requiresImmediateAttention(NotificationMessage message) {
        validateMessage(message);

        return message.getPriority().requiresImmediateAttention()
                || message.isSecurityNotification();
    }

    public static boolean shouldNotifyCustomer(NotificationMessage message) {
        validateMessage(message);

        return message.getRecipientType() == NotificationRecipientType.CUSTOMER_CONTACT
                && message.isCustomerVisible();
    }

    public static boolean isOperationalNotification(NotificationMessage message) {
        validateMessage(message);

        return message.isOperationalNotification();
    }

    public static boolean isFinancialNotification(NotificationMessage message) {
        validateMessage(message);

        return message.isFinancialNotification();
    }

    public static boolean isSecurityNotification(NotificationMessage message) {
        validateMessage(message);

        return message.isSecurityNotification();
    }

    public static boolean usesExternalChannel(NotificationMessage message) {
        validateMessage(message);

        return message.isExternalChannel();
    }

    private static void validateMessage(NotificationMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("La notifica è obbligatoria.");
        }
    }
}
