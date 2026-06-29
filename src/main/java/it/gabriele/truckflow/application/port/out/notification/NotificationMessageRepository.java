package it.gabriele.truckflow.application.port.out.notification;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.notification.NotificationMessage;

/** Repository port per NotificationMessage. L'implementazione sarà in infrastructure. */
public interface NotificationMessageRepository extends RepositoryPort<NotificationMessage> {}
