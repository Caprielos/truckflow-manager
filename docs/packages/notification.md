# Package `notification` — Notifiche

## Scopo

Messaggi, canali, destinatari, priorità e stati di invio.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `NotificationChannel` | Enum | Valori controllati usati dalle regole di dominio. |
| `NotificationMessage` | Classe | Classe di dominio del package. |
| `NotificationPriority` | Enum | Valori controllati usati dalle regole di dominio. |
| `NotificationRecipientType` | Enum | Valori controllati usati dalle regole di dominio. |
| `NotificationRules` | Classe | Classe di regole di business del package. |
| `NotificationStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `NotificationType` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `NotificationChannel`

Valori: `EMAIL`, `SMS`, `PUSH`, `IN_APP`, `WEBHOOK`.

### `NotificationPriority`

Valori: `LOW`, `NORMAL`, `HIGH`, `URGENT`.

### `NotificationRecipientType`

Valori: `CUSTOMER_CONTACT`, `DRIVER`, `DISPATCHER`, `ADMIN`, `INTEGRATION`, `SYSTEM`.

### `NotificationStatus`

Valori: `DRAFT`, `SCHEDULED`, `SENT`, `FAILED`, `CANCELLED`.

### `NotificationType`

Valori: `SHIPMENT_PLANNED`, `SHIPMENT_DELAYED`, `PICKUP_COMPLETED`, `DELIVERY_COMPLETED`, `DOCUMENT_REQUESTED`, `DOCUMENT_VERIFIED`, `INVOICE_ISSUED`, `PAYMENT_RECEIVED`, `CLAIM_UPDATED`, `MAINTENANCE_ALERT`, `SECURITY_ALERT`, `SYSTEM_ALERT`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/notification
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
