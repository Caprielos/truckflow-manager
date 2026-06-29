package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.NotificationMessageRepository;
import it.gabriele.truckflow.domain.notification.NotificationMessage;

/** Repository in memoria per NotificationMessage. */
public final class InMemoryNotificationMessageRepository extends InMemoryRepository<NotificationMessage> implements NotificationMessageRepository {

    public InMemoryNotificationMessageRepository() {
        super(item -> item.getNotificationNumber());
    }
}
