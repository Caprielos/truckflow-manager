# Package `notification` — Notifiche

Modella messaggi, canali, priorità, destinatari e stato notifica.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/notification
```

## Classi

- `NotificationChannel`
- `NotificationMessage`
- `NotificationPriority`
- `NotificationRecipientType`
- `NotificationRules`
- `NotificationStatus`
- `NotificationType`

## Test collegati

- `NotificationMessageTest`
- `NotificationRulesTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
