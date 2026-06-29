package it.gabriele.truckflow.infrastructure.memory.notification;

import it.gabriele.truckflow.application.port.out.notification.NotificationMessageRepository;
import it.gabriele.truckflow.domain.notification.NotificationMessage;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per NotificationMessage. */
public final class InMemoryNotificationMessageRepository
    extends InMemoryRepository<NotificationMessage> implements NotificationMessageRepository {

  public InMemoryNotificationMessageRepository() {
    super(item -> item.getNotificationNumber());
  }
}
