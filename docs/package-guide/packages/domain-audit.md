# `domain/audit`

Traccia modifiche e azioni importanti: chi ha fatto cosa, quando e con che gravità.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `AuditActionType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | dataChange, securitySensitive, financialImpact | isDataChange, isSecuritySensitive, hasFinancialImpact |
| `AuditActorType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | humanActor | isHumanActor |
| `AuditEvent` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, eventId, aggregateType, aggregateId, actorType, actorId, actionType, severity | of, userAction, systemAction, integrationAction, getEventId, getAggregateType, getAggregateId, getActorType |
| `AuditRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canAppendEvent, requiresReview, containsSecuritySensitiveEvents, containsFinancialImpactEvents, isChronological |
| `AuditSeverity` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | level, requiresReview | getLevel, requiresReview, isAtLeast |
| `AuditTrail` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | events, first, uniqueEventIds | of, getEvents, getEventCount, getAggregateType, getAggregateId, getFirstEvent, getLatestEvent, containsEventId |
