# Package `notification` — Notification

## Scopo

Modella messaggi e notifiche verso utenti, clienti, autisti o operatori.

## Concetti principali

- `NotificationMessage`
- `NotificationType`
- `NotificationChannel`
- `NotificationRecipientType`
- `NotificationPriority`
- `NotificationStatus`
- `NotificationRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `NotificationChannel` | enum | Enum di classificazione/valori ammessi. |
| `NotificationMessage` | final class | Entity o value object del package. |
| `NotificationPriority` | enum | Enum di classificazione/valori ammessi. |
| `NotificationRecipientType` | enum | Enum di classificazione/valori ammessi. |
| `NotificationRules` | final class | Classe statica di regole di business del package. |
| `NotificationStatus` | enum | Enum di classificazione/valori ammessi. |
| `NotificationType` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `NotificationChannel`: `EMAIL`, `SMS`, `PUSH`, `IN_APP`, `WEBHOOK`
- `NotificationPriority`: `LOW`, `NORMAL`, `HIGH`, `URGENT`
- `NotificationRecipientType`: `CUSTOMER_CONTACT`, `DRIVER`, `DISPATCHER`, `ADMIN`, `INTEGRATION`, `SYSTEM`
- `NotificationStatus`: `DRAFT`, `SCHEDULED`, `SENT`, `FAILED`, `CANCELLED`
- `NotificationType`: `SHIPMENT_PLANNED`, `SHIPMENT_DELAYED`, `PICKUP_COMPLETED`, `DELIVERY_COMPLETED`, `DOCUMENT_REQUESTED`, `DOCUMENT_VERIFIED`, `INVOICE_ISSUED`, `PAYMENT_RECEIVED`, `CLAIM_UPDATED`, `MAINTENANCE_ALERT`, `SECURITY_ALERT`, `SYSTEM_ALERT`

## Regole di business

- Priorità e canale dipendono dal tipo notifica.
- Scadenze e alert possono generare messaggi.

## Collegamenti con altri package

- compliance, maintenance, tracking, identity

## Test collegati

- `NotificationMessageTest.java`
- `NotificationRulesTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
