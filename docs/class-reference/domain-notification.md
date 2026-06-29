# Domain `notification` spiegato

Messaggi, notifiche, canali e regole di invio.

## Classi principali

### `NotificationChannel`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `externalChannel`
- `realTime`

Metodi pubblici principali:

- `isExternalChannel()`
- `isRealTime()`

### `NotificationMessage`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_NOTIFICATION_NUMBER_LENGTH`
- `MAX_RECIPIENT_REFERENCE_LENGTH`
- `MAX_SUBJECT_LENGTH`
- `MAX_BODY_LENGTH`
- `notificationNumber`
- `type`
- `channel`
- `recipientType`
- `recipientReference`
- `priority`
- `subject`
- `body`

Metodi pubblici principali:

- `draft()`
- `scheduled()`
- `schedule()`
- `send()`
- `fail()`
- `cancel()`
- `getNotificationNumber()`
- `getType()`
- `getChannel()`
- `getRecipientType()`
- `getRecipientReference()`
- `getPriority()`

### `NotificationPriority`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `level`
- `immediateAttention`

Metodi pubblici principali:

- `getLevel()`
- `requiresImmediateAttention()`
- `isAtLeast()`

### `NotificationRecipientType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `humanRecipient`

Metodi pubblici principali:

- `isHumanRecipient()`

### `NotificationRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canBeScheduled()`
- `canBeSent()`
- `canBeFailed()`
- `canBeCancelled()`
- `isTerminal()`
- `requiresImmediateAttention()`
- `shouldNotifyCustomer()`
- `isOperationalNotification()`
- `isFinancialNotification()`
- `isSecurityNotification()`
- `usesExternalChannel()`

### `NotificationStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`
- `delivered`

Metodi pubblici principali:

- `isTerminal()`
- `isDelivered()`

### `NotificationType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `customerVisible`
- `operational`
- `financial`
- `security`

Metodi pubblici principali:

- `isCustomerVisible()`
- `isOperational()`
- `isFinancial()`
- `isSecurity()`
