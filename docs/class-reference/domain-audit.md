# Package `domain.audit`

Audit trail: chi ha fatto cosa, quando e con quale severità.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| AuditActionType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isDataChange, isSecuritySensitive, hasFinancialImpact |
| AuditActorType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isHumanActor |
| AuditEvent | class | Evento puntuale nella timeline operativa o audit. | of, userAction, systemAction, integrationAction, getEventId, getAggregateType, getAggregateId, getActorType, getActorId, getActionType |
| AuditRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.audit. | canAppendEvent, requiresReview, containsSecuritySensitiveEvents, containsFinancialImpactEvents, isChronological |
| AuditSeverity | enum | Enum: insieme chiuso di valori ammessi dal dominio. | getLevel, requiresReview, isAtLeast |
| AuditTrail | class | Classe del package domain.audit; rappresenta un concetto del modello TruckFlow. | of, getEvents, getEventCount, getAggregateType, getAggregateId, getFirstEvent, getLatestEvent, containsEventId, hasActionType, hasSecuritySensitiveEvents |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
