package it.gabriele.truckflow.domain.notification;

/**
 * Canale usato per inviare una notifica.
 */
public enum NotificationChannel {

    EMAIL(true, false),
    SMS(true, true),
    PUSH(true, true),
    IN_APP(false, true),
    WEBHOOK(true, true);

    private final boolean externalChannel;
    private final boolean realTime;

    NotificationChannel(boolean externalChannel, boolean realTime) {
        this.externalChannel = externalChannel;
        this.realTime = realTime;
    }

    public boolean isExternalChannel() {
        return externalChannel;
    }

    public boolean isRealTime() {
        return realTime;
    }
}
