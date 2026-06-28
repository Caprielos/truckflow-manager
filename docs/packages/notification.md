# Package `notification` — Notifiche

Rappresenta messaggi, canali, destinatari, priorità e stati di invio.

## Responsabilità

- Notifica eventi importanti a clienti, driver, dispatcher o sistemi.
- NotificationRules impedisce stati incoerenti.

## Classi

- `NotificationChannel` — enum con valori: `EMAIL`, `SMS`, `PUSH`, `IN_APP`, `WEBHOOK`.
- `NotificationMessage` — modello/domain object del package.
- `NotificationPriority` — enum con valori: `LOW`, `NORMAL`, `HIGH`, `URGENT`.
- `NotificationRecipientType` — enum con valori: `CUSTOMER_CONTACT`, `DRIVER`, `DISPATCHER`, `ADMIN`, `INTEGRATION`, `SYSTEM`.
- `NotificationRules` — classe di regole pure del package.
- `NotificationStatus` — enum con valori: `DRAFT`, `SCHEDULED`, `SENT`, `FAILED`, `CANCELLED`.
- `NotificationType` — enum con valori: `SHIPMENT_PLANNED`, `SHIPMENT_DELAYED`, `PICKUP_COMPLETED`, `DELIVERY_COMPLETED`, `DOCUMENT_REQUESTED`, `DOCUMENT_VERIFIED`, `INVOICE_ISSUED`, `PAYMENT_RECEIVED`, `CLAIM_UPDATED`, `MAINTENANCE_ALERT`, `SECURITY_ALERT`, `SYSTEM_ALERT`.

## Collegamenti

- Notifica eventi importanti a clienti, driver, dispatcher o sistemi.
- NotificationRules impedisce stati incoerenti.
