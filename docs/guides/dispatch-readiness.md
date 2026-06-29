# Dispatch readiness

## Dove si trova

```text
src/main/java/it/gabriele/truckflow/domain/dispatch
```

## Obiettivo

Il dispatch rappresenta l'ufficio traffico. Serve a scegliere la combinazione migliore per una missione.

Un candidato può essere valutato su:

- autista;
- veicolo;
- rimorchio/semirimorchio;
- convoglio già pronto;
- parcheggio;
- documenti;
- compliance;
- margine economico;
- ore guida;
- disponibilità;
- blocchi operativi.

## Classi principali

```text
DispatchPlan
DispatchAssignmentCandidate
DispatchCheckResult
DispatchCheckType
DispatchReadinessStatus
DispatchRules
```

## Stati possibili

Il modello distingue candidati assegnabili da candidati bloccati.

Esempio:

```text
Candidato 1:
- autista CE + ADR valido
- trattore + semirimorchio frigo già parcheggiati insieme
- documenti ok
- margine positivo
→ assegnabile

Candidato 2:
- autista senza ADR
- cargo ADR
→ bloccato
```

## Perché sta nel domain

Le regole di compatibilità sono business logic. Il salvataggio dati e l'orchestrazione finale andranno nell'application layer.
