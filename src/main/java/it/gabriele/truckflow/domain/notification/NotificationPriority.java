package it.gabriele.truckflow.domain.notification;

/**
 * Priorità della notifica.
 */
public enum NotificationPriority {

    LOW(1, false),
    NORMAL(2, false),
    HIGH(3, true),
    URGENT(4, true);

    private final int level;
    private final boolean immediateAttention;

    NotificationPriority(int level, boolean immediateAttention) {
        this.level = level;
        this.immediateAttention = immediateAttention;
    }

    public int getLevel() {
        return level;
    }

    public boolean requiresImmediateAttention() {
        return immediateAttention;
    }

    public boolean isAtLeast(NotificationPriority other) {
        if (other == null) {
            throw new IllegalArgumentException("La priorità da confrontare è obbligatoria.");
        }

        return level >= other.level;
    }
}
