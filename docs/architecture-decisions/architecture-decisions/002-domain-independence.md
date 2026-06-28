# ADR 002 — Domain indipendente da framework e infrastruttura

## Stato

Accettata

## Contesto

TruckFlow Manager deve crescere in modo ordinato. Il domain contiene regole di business e non deve essere legato a database, web framework o provider esterni.

## Decisione

Il domain resta Java puro.

Nel domain non si usano:

- Spring annotations;
- JPA annotations;
- repository tecnici;
- controller REST;
- HTTP client;
- filesystem;
- provider esterni.

## Motivazioni

Questa scelta permette di:

- testare il domain con unit test semplici;
- cambiare database senza riscrivere le regole;
- aggiungere API REST senza sporcare il domain;
- aggiungere provider esterni senza modificare entity e value object.

## Conseguenze

Le integrazioni future saranno in application/infrastructure.

Il domain può definire concetti come `RouteCostEstimate`, ma non deve sapere come viene calcolata una stima reale.
