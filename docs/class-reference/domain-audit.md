# Domain `audit` spiegato

Traccia modifiche e azioni importanti: chi ha fatto cosa, quando e con che gravità.

## Classi principali

### `AuditActionType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `dataChange`
- `securitySensitive`
- `financialImpact`

Metodi pubblici principali:

- `isDataChange()`
- `isSecuritySensitive()`
- `hasFinancialImpact()`

### `AuditActorType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `humanActor`

Metodi pubblici principali:

- `isHumanActor()`

### `AuditEvent`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `eventId`
- `aggregateType`
- `aggregateId`
- `actorType`
- `actorId`
- `actionType`
- `severity`
- `occurredAt`
- `notes`

Metodi pubblici principali:

- `of()`
- `userAction()`
- `systemAction()`
- `integrationAction()`
- `getEventId()`
- `getAggregateType()`
- `getAggregateId()`
- `getActorType()`
- `getActorId()`
- `getActionType()`
- `getSeverity()`
- `getOccurredAt()`

### `AuditRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canAppendEvent()`
- `requiresReview()`
- `containsSecuritySensitiveEvents()`
- `containsFinancialImpactEvents()`
- `isChronological()`

### `AuditSeverity`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `level`
- `requiresReview`

Metodi pubblici principali:

- `getLevel()`
- `requiresReview()`
- `isAtLeast()`

### `AuditTrail`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `events`
- `first`
- `uniqueEventIds`

Metodi pubblici principali:

- `of()`
- `getEvents()`
- `getEventCount()`
- `getAggregateType()`
- `getAggregateId()`
- `getFirstEvent()`
- `getLatestEvent()`
- `containsEventId()`
- `hasActionType()`
- `hasSecuritySensitiveEvents()`
- `hasFinancialImpactEvents()`
- `hasReviewRequiredEvents()`
